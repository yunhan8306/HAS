package com.myStash.android.feature.item.has

import androidx.compose.foundation.text2.input.TextFieldState
import androidx.compose.foundation.text2.input.clearText
import androidx.compose.foundation.text2.input.setTextAndPlaceCursorAtEnd
import androidx.compose.foundation.text2.input.textAsFlow
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myStash.android.common.util.offerOrRemove
import com.myStash.android.common.util.removeBlank
import com.myStash.android.core.data.usecase.has.SaveHasUseCase
import com.myStash.android.core.data.usecase.tag.CheckAvailableTagUseCase
import com.myStash.android.core.data.usecase.tag.GetTagListUseCase
import com.myStash.android.core.data.usecase.tag.SaveTagUseCase
import com.myStash.android.core.di.DefaultDispatcher
import com.myStash.android.core.model.Has
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
class AddHasViewModel @Inject constructor(
    private val checkAvailableTagUseCase: CheckAvailableTagUseCase,

    private val getTagListUseCase: GetTagListUseCase,

    private val saveHasUseCase: SaveHasUseCase,
    private val saveTagUseCase: SaveTagUseCase,
    private val stateHandle: SavedStateHandle,
    // dispatcher
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
): ContainerHost<AddHasScreenState, AddHasSideEffect>, ViewModel() {
    override val container: Container<AddHasScreenState, AddHasSideEffect> =
        container(AddHasScreenState())

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

    fun addImage(uri: String) {
        intent {
            reduce {
                state.copy(
                    imageUri = uri
                )
            }
        }
    }

    fun removeImage() {
        intent {
            reduce {
                state.copy(
                    imageUri = null
                )
            }
        }
    }

    fun selectType(type: Type?) {
        intent {
            viewModelScope.launch {
                reduce {
                    state.copy(
                        selectedType = if(state.selectedType == type) null else type
                    )
                }
                typeTextState = TextFieldState(initialText = state.selectedType?.name ?: "")
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

    fun saveItem() {
        intent {
            viewModelScope.launch {
                val tagIdList = selectedTagList.map { tag ->
                    if(tag.id == null) saveTagUseCase.invoke(tag)
                    else tag.id!!
                }

                // 기존에 없던 tag 생성
                selectedTagList.filter { it.id == null }.forEach { tag ->
                    val id = saveTagUseCase.invoke(tag)
                    selectedTagList.offerOrRemove(tag) { it.name == tag.name }
                    selectedTagList.add(tag.copy(id = id))
                }

                // essential item 생성
                val saveHas = Has(
                    imagePath = state.imageUri,
                    tags = selectedTagList.map { it.id!! },
                    type = state.selectedType?.id!!,
                )

                saveHasUseCase.invoke(saveHas)

                postSideEffect(AddHasSideEffect.Finish)
            }
        }
    }
}