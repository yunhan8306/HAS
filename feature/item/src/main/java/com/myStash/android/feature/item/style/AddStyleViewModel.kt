package com.myStash.android.feature.item.style

import android.content.Intent
import androidx.compose.foundation.text2.input.TextFieldState
import androidx.compose.foundation.text2.input.setTextAndPlaceCursorAtEnd
import androidx.compose.foundation.text2.input.textAsFlow
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myStash.android.common.util.offerOrRemove
import com.myStash.android.common.util.removeBlank
import com.myStash.android.core.data.usecase.has.GetHasListUseCase
import com.myStash.android.core.data.usecase.style.SaveStyleUseCase
import com.myStash.android.core.data.usecase.tag.GetTagListUseCase
import com.myStash.android.core.data.usecase.tag.SaveTagUseCase
import com.myStash.android.core.data.usecase.type.GetTypeListUseCase
import com.myStash.android.core.di.DefaultDispatcher
import com.myStash.android.core.model.Has
import com.myStash.android.core.model.Style
import com.myStash.android.core.model.Type
import com.myStash.android.core.model.getTotalType
import com.myStash.android.core.model.getTotalTypeList
import com.myStash.android.core.model.searchSelectedTypeHasList
import com.myStash.android.feature.item.ItemConstants
import com.myStash.android.feature.item.has.AddHasSideEffect
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
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
    private val saveTagUseCase: SaveTagUseCase,
    private val stateHandle: SavedStateHandle,

    private val getHasListUseCase: GetHasListUseCase,
    private val getTypeListUseCase: GetTypeListUseCase,
    // dispatcher
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
): ContainerHost<AddStyleScreenState, AddStyleSideEffect>, ViewModel() {
    override val container: Container<AddStyleScreenState, AddStyleSideEffect> =
        container(AddStyleScreenState())

    init {
        fetch()
        collectSearchText()
    }

    val searchTextState = TextFieldState()
    var selectedType = getTotalType()

    private val searchHasList = searchTextState
        .textAsFlow()
        .flowOn(defaultDispatcher)
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
        .map { getTotalTypeList() + it }
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
                val selectedHasIdList = stateHandle.get<Array<Long>>("style")?.toList() ?: emptyList()

                combine(typeTotalList, hasTotalList, tagTotalList) { typeList, hasList, tagTotalList ->
                    Triple(typeList, hasList, tagTotalList)
                }.collectLatest { (typeList, hasList, _) ->
                    reduce {
                        state.copy(
                            typeTotalList = typeList,
                            hasList = hasList,
                            selectedType = selectedType,
                            selectedHasList = hasList.filter { selectedHasIdList.contains(it.id) }
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

    fun selectType(type: Type) {
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

    fun selectHas(has: Has) {
        intent {
            viewModelScope.launch {
                val selectedHasList = state.selectedHasList.toMutableList().apply { offerOrRemove(has) { it.id == has.id } }
                reduce {
                    state.copy(
                        selectedHasList = selectedHasList.toList()
                    )
                }
            }
        }
    }

    fun saveStyle() {
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

    private fun getHasList(text: String): List<Has> {
        return text.searchSelectedTypeHasList(
            tagTotalList = tagTotalList.value,
            hasTotalList = hasTotalList.value,
            type = selectedType
        )
    }
}