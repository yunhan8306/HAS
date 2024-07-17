package com.myStash.android.feature.item.style

import androidx.compose.foundation.text2.input.TextFieldState
import androidx.compose.foundation.text2.input.clearText
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
import com.myStash.android.core.model.Tag
import com.myStash.android.core.model.Type
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
    }

    val searchTextState = TextFieldState()

    private val searchHasList = searchTextState
        .textAsFlow()
        .flowOn(defaultDispatcher)
        .onEach { text -> searchTextState.setTextAndPlaceCursorAtEnd(text.removeBlank()) }
        .map { search ->
            if(search.isNotEmpty()) {
                val tagIdList = tagTotalList.value.filter { it.name.contains(search) }.map { it.id }
                hasTotalList.value.filter { tagIdList.contains(it.id) }
            } else {
                hasTotalList.value
            }
        }

    private val tagTotalList = getTagListUseCase.tagList
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = emptyList()
        )

    private val typeTotalList = getTypeListUseCase.typeList
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
                val selectedHasIdList = stateHandle.get<Set<Long>>("style")?.toList() ?: emptyList()

                combine(typeTotalList, hasTotalList, tagTotalList) { typeList, hasList, _ ->
                    reduce {
                        state.copy(
                            typeTotalList = typeList,
                            hasList = hasList,
                            selectedHasList = hasList.filter { selectedHasIdList.contains(it.id) }
                        )
                    }
                }

                collectSearchText()
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

    fun selectType(type: Type?) {
        intent {
            viewModelScope.launch {
                reduce {
                    state.copy(
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
                postSideEffect(AddStyleSideEffect.Finish)
            }
        }
    }
}