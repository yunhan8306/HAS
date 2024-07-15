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
import com.myStash.android.core.data.usecase.style.SaveStyleUseCase
import com.myStash.android.core.data.usecase.tag.GetTagListUseCase
import com.myStash.android.core.data.usecase.tag.SaveTagUseCase
import com.myStash.android.core.di.DefaultDispatcher
import com.myStash.android.core.model.Has
import com.myStash.android.core.model.Style
import com.myStash.android.core.model.Tag
import com.myStash.android.core.model.Type
import com.myStash.android.core.model.testWomanTypeTotalList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
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
    // dispatcher
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
): ContainerHost<AddStyleScreenState, AddStyleSideEffect>, ViewModel() {
    override val container: Container<AddStyleScreenState, AddStyleSideEffect> =
        container(AddStyleScreenState())

    init {
        fetch()
        collectSearchText()
    }

    private val selectedTagList = mutableListOf<Tag>()

    val newTagList = mutableListOf<String>()

    var typeTextState = TextFieldState()

    val searchTextState = TextFieldState()

    private val searchTagList = searchTextState
        .textAsFlow()
        .flowOn(defaultDispatcher)
        .onEach { text -> searchTextState.setTextAndPlaceCursorAtEnd(text.removeBlank()) }
        .map { search -> tagTotalList.value.filter { it.name.contains(search) } }
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

    private fun fetch() {
        intent {
            viewModelScope.launch {
                val has = stateHandle.get<Has?>("has")

                // type total list 호출
                tagTotalList.collectLatest {
                    reduce {
                        state.copy(
                            tagTotalList = it,
                            typeTotalList = testWomanTypeTotalList,
                            searchTagList = it
                        )
                    }
                }
            }
        }
    }

    private fun collectSearchText() {
        intent {
            viewModelScope.launch {
                searchTagList.collectLatest {
                    reduce {
                        state.copy(searchTagList = it.toList())
                    }
                }
            }
        }
    }

    fun deleteSearchText() {
        searchTextState.clearText()
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

    fun selectTag(tag: Tag) {
        intent {
            viewModelScope.launch {
                selectedTagList.offerOrRemove(tag) { it.name == tag.name }
                reduce {
                    state.copy(
                        selectedTagList = selectedTagList.toList()
                    )
                }
            }
        }
    }

    fun saveStyle() {
        intent {
            viewModelScope.launch {

                val saveStyle = Style()

                saveStyleUseCase.invoke(saveStyle)

                postSideEffect(AddStyleSideEffect.Finish)
            }
        }
    }
}