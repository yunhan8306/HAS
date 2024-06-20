package com.myStash.android.feature.item

import android.util.Log
import androidx.compose.foundation.text2.input.TextFieldState
import androidx.compose.foundation.text2.input.clearText
import androidx.compose.foundation.text2.input.setTextAndPlaceCursorAtEnd
import androidx.compose.foundation.text2.input.setTextAndSelectAll
import androidx.compose.foundation.text2.input.textAsFlow
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myStash.android.common.util.offerOrRemove
import com.myStash.android.common.util.removeBlank
import com.myStash.android.core.data.usecase.item.SaveItemUseCase
import com.myStash.android.core.data.usecase.tag.CheckAvailableTagUseCase
import com.myStash.android.core.data.usecase.tag.GetTagListUseCase
import com.myStash.android.core.data.usecase.tag.SaveTagUseCase
import com.myStash.android.core.di.DefaultDispatcher
import com.myStash.android.core.model.Item
import com.myStash.android.core.model.Tag
import com.myStash.android.core.model.Type
import com.myStash.android.core.model.testTagList
import com.myStash.android.core.model.testWomanTypeTotalList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
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
class ItemEssentialViewModel @Inject constructor(
    private val checkAvailableTagUseCase: CheckAvailableTagUseCase,

    private val getTagListUseCase: GetTagListUseCase,

    private val saveItemUseCase: SaveItemUseCase,
    private val saveTagUseCase: SaveTagUseCase,
    private val stateHandle: SavedStateHandle,
    // dispatcher
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
): ContainerHost<EssentialItemScreenState, EssentialItemSideEffect>, ViewModel() {
    override val container: Container<EssentialItemScreenState, EssentialItemSideEffect> =
        container(EssentialItemScreenState())

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
        .map { search -> testTagList.filter { it.name.contains(search) } }
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
                val item = stateHandle.get<Item?>("item")

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
                val saveItem = Item(
                    imagePath = state.imageUri,
                    tags = selectedTagList.map { it.id!! },
                    type = state.selectedType?.id!!,
                )

                saveItemUseCase.invoke(saveItem)

                postSideEffect(EssentialItemSideEffect.Finish)
            }
        }
    }
}