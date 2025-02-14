package com.has.android.feature.item.style

import android.content.Intent
import androidx.compose.foundation.text2.input.TextFieldState
import androidx.compose.foundation.text2.input.setTextAndPlaceCursorAtEnd
import androidx.compose.foundation.text2.input.textAsFlow
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.has.android.common.util.Quadruple
import com.has.android.common.util.constants.ItemConstants
import com.has.android.common.util.removeBlank
import com.has.android.core.model.Has
import com.has.android.core.model.Style
import com.has.android.core.model.Type
import com.has.android.core.model.checkTypeAndSelectHas
import com.has.android.core.model.getTotalType
import com.has.android.core.model.getTotalTypeList
import com.has.android.core.model.getUnSelectTypeList
import com.has.android.core.model.searchSelectedTypeHasList
import com.has.android.domain.database.database.usecase.has.GetHasListUseCase
import com.has.android.domain.database.database.usecase.style.SaveStyleUseCase
import com.has.android.domain.database.database.usecase.style.UpdateStyleUseCase
import com.has.android.domain.database.database.usecase.tag.GetTagListUseCase
import com.has.android.domain.database.database.usecase.type.GetTypeListUseCase
import com.has.android.feature.item.item.ItemTab
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class AddStyleViewModel @Inject constructor(
    private val getTagListUseCase: GetTagListUseCase,
    private val saveStyleUseCase: SaveStyleUseCase,
    private val updateStyleUseCase: UpdateStyleUseCase,
    private val stateHandle: SavedStateHandle,

    private val getHasListUseCase: GetHasListUseCase,
    private val getTypeListUseCase: GetTypeListUseCase,
): ContainerHost<AddStyleScreenState, AddStyleSideEffect>, ViewModel() {
    override val container: Container<AddStyleScreenState, AddStyleSideEffect> =
        container(AddStyleScreenState())

    init {
        fetch()
        collectSearchText()
    }

    val searchTextState = TextFieldState()
    var selectedType = getTotalType()

    private val agoHasIdList = stateHandle.get<Array<Long>>(ItemConstants.CMD_STYLE)?.toList() ?: emptyList()
    private val agoStyleId = stateHandle.get<Long?>(ItemConstants.CMD_STYLE_ID)
    private val isEdit = stateHandle.get<String>(ItemConstants.CMD_EDIT_TAB_NAME) == ItemTab.STYLE.tabName

    private val searchHasList = searchTextState
        .textAsFlow()
        .flowOn(Dispatchers.Default)
        .onEach { text -> searchTextState.setTextAndPlaceCursorAtEnd(text.removeBlank()) }
        .map { search -> getHasList(search.toString()) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = emptyList()
        )

    private val tagTotalList = getTagListUseCase.tagList
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = emptyList()
        )

    private val typeTotalList = getTypeListUseCase.typeList
        .map { getTotalTypeList() + it + getUnSelectTypeList() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = emptyList()
        )

    private val typeRemoveList = getTypeListUseCase.typeRemoveList
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = emptyList()
        )

    private val hasTotalList = getHasListUseCase.hasList
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = emptyList()
        )

    private fun fetch() {
        intent {
            viewModelScope.launch {
                combine(typeTotalList, hasTotalList, tagTotalList, typeRemoveList) { typeList, hasList, tagTotalList, typeRemoveList ->
                    Quadruple(typeList, hasList, tagTotalList, typeRemoveList)
                }.collectLatest { (typeList, hasList, _, _) ->
                    reduce {
                        state.copy(
                            isEdit = isEdit,
                            typeTotalList = typeList,
                            hasList = hasList,
                            selectedHasList = hasList.filter { agoHasIdList.contains(it.id) }.sortedBy { it.type }
                        )
                    }
                }
            }
        }
    }

    private fun collectSearchText() {
        intent {
            viewModelScope.launch {
                searchHasList.collectLatest {
                    reduce {
                        state.copy(hasList = it.toList())
                    }
                }
            }
        }
    }

    fun onAction(action: AddStyleScreenAction) {
        when(action) {
            is AddStyleScreenAction.SelectType -> {
                selectType(action.type)
            }
            is AddStyleScreenAction.SelectHas -> {
                selectHas(action.has)
            }
            is AddStyleScreenAction.SaveStyle -> {
                if(isEdit) editStyle() else saveStyle()
            }
        }
    }

    private fun selectType(type: Type) {
        intent {
            viewModelScope.launch {
                selectedType = type
                reduce {
                    state.copy(
                        hasList = getHasList(searchTextState.text.toString()),
                        selectedType = type
                    )
                }
            }
        }
    }

    private fun selectHas(has: Has) {
        intent {
            viewModelScope.launch {
                reduce {
                    state.copy(
                        selectedHasList = state.selectedHasList.checkTypeAndSelectHas(has)
                    )
                }
            }
        }
    }

    private fun saveStyle() {
        intent {
            viewModelScope.launch {
                val saveStyle = Style(
                    hass = state.selectedHasList.map { it.id!! }
                )
                saveStyleUseCase.invoke(saveStyle)

                Intent().apply {
                    putExtra(ItemConstants.CMD_COMPLETE, ItemConstants.CMD_STYLE)
                    postSideEffect(AddStyleSideEffect.Finish(this))
                }
            }
        }
    }

    private fun editStyle() {
        intent {
            viewModelScope.launch {
                val editStyle = Style(
                    id = agoStyleId,
                    hass = state.selectedHasList.map { it.id!! }
                )
                saveStyleUseCase.invoke(editStyle)

                Intent().apply {
                    putExtra(ItemConstants.CMD_COMPLETE, ItemConstants.CMD_STYLE)
                    postSideEffect(AddStyleSideEffect.Finish(this))
                }
            }
        }
    }

    private fun getHasList(text: String): List<Has> {
        return text.searchSelectedTypeHasList(
            typeRemoveList = typeRemoveList.value,
            tagTotalList = tagTotalList.value,
            hasTotalList = hasTotalList.value,
            type = selectedType
        )
    }
}