package com.has.android.feature.item.has

import android.content.Intent
import androidx.compose.foundation.text2.input.TextFieldState
import androidx.compose.foundation.text2.input.clearText
import androidx.compose.foundation.text2.input.setTextAndPlaceCursorAtEnd
import androidx.compose.foundation.text2.input.textAsFlow
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.has.android.common.util.offer
import com.has.android.common.util.offerOrRemove
import com.has.android.core.data.usecase.has.SaveHasUseCase
import com.has.android.core.data.usecase.has.UpdateHasUseCase
import com.has.android.core.data.usecase.tag.CheckAvailableTagUseCase
import com.has.android.core.data.usecase.tag.GetTagListUseCase
import com.has.android.core.data.usecase.tag.SaveTagUseCase
import com.has.android.core.data.usecase.type.GetTypeListUseCase
import com.has.android.core.di.DefaultDispatcher
import com.has.android.core.model.Has
import com.has.android.core.model.Tag
import com.has.android.core.model.Type
import com.has.android.core.model.getTagList
import com.has.android.core.model.getType
import com.has.android.core.model.tagAddAndFoundFromSearchText
import com.has.android.common.util.constants.ItemConstants
import com.has.android.feature.item.item.ItemTab
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
class AddHasViewModel @Inject constructor(
    private val checkAvailableTagUseCase: CheckAvailableTagUseCase,

    private val getTagListUseCase: GetTagListUseCase,
    private val getTypeListUseCase: GetTypeListUseCase,
    private val saveHasUseCase: SaveHasUseCase,
    private val updateHasUseCase: UpdateHasUseCase,
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

    val searchTextState = TextFieldState()
    private val agoHas = stateHandle.get<Has?>(ItemConstants.CMD_HAS)
    private val isEdit = stateHandle.get<String>(ItemConstants.CMD_EDIT_TAB_NAME) == ItemTab.HAS.tabName

    private val searchTagList = searchTextState
        .textAsFlow()
        .flowOn(defaultDispatcher)
        .onEach { text ->
            tagTotalList.value.tagAddAndFoundFromSearchText(
                text = text,
                add = { addText -> selectedTagList.offer(Tag(name = addText)) { addText == it.name} },
                found = { tag -> selectedTagList.offer(tag) { tag.name == it.name } },
                complete = { searchTextState.setTextAndPlaceCursorAtEnd(it) }
            )
        }
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

    private val typeTotalList = getTypeListUseCase.typeList
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = emptyList()
        )

    private fun fetch() {
        intent {
            viewModelScope.launch {
                combine(tagTotalList, typeTotalList) { tagList, typeList ->
                    Pair(tagList, typeList)
                }.collectLatest { (tagList, typeList) ->
                    if(selectedTagList.isEmpty()) {
                        selectedTagList.addAll(agoHas?.tags?.getTagList(tagList) ?: emptyList())
                    }

                    reduce {
                        state.copy(
                            isEdit = isEdit,
                            imageUri = state.imageUri ?: agoHas?.imagePath,
                            typeTotalList = typeList,
                            selectedType = state.selectedType ?: agoHas?.type?.getType(typeList),
                            tagTotalList = tagList,
                            selectedTagList = selectedTagList.toList(),
                            searchTagList = tagList
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
                        state.copy(
                            searchTagList = it.toList(),
                            selectedTagList = selectedTagList.toList()
                        )
                    }
                }
            }
        }
    }

    fun onAction(action: AddHasScreenAction) {
        when(action) {
            is AddHasScreenAction.UnselectImage -> {
                unselectImage(action.uri)
            }
            is AddHasScreenAction.SelectType -> {
                selectType(action.type)
            }
            is AddHasScreenAction.SelectTag -> {
                selectTag(action.tag)
            }
            is AddHasScreenAction.Save -> {
                if(isEdit) updateItem() else saveItem()
            }
            else -> Unit
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

    private fun unselectImage(uri: String) {
        intent {
            reduce {
                state.copy(
                    imageUri = null
                )
            }
        }
    }

    private fun selectType(type: Type?) {
        intent {
            viewModelScope.launch {
                reduce {
                    state.copy(
                        selectedType = if(state.selectedType == type) null else type
                    )
                }
            }
        }
    }

    private fun selectTag(tag: Tag) {
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

    private fun saveItem() {
        intent {
            viewModelScope.launch {
                val newHas = Has(
                    imagePath = state.imageUri,
                    tags = getTagIdList(),
                    type = state.selectedType?.id!!,
                )

                saveHasUseCase.invoke(newHas)

                Intent().apply {
                    putExtra(ItemConstants.CMD_COMPLETE, ItemConstants.CMD_HAS)
                    postSideEffect(AddHasSideEffect.Finish(this))
                }
            }
        }
    }

    private fun updateItem() {
        intent {
            viewModelScope.launch {
                val updateHas = Has(
                    id = agoHas?.id,
                    tags = getTagIdList(),
                    type = state.selectedType?.id!!,
                    imagePath = state.imageUri,
                )

                updateHasUseCase.invoke(updateHas)

                Intent().apply {
                    putExtra(ItemConstants.CMD_COMPLETE, ItemConstants.CMD_HAS)
                    postSideEffect(AddHasSideEffect.Finish(this))
                }
            }
        }
    }

    private suspend fun getTagIdList(): List<Long> {
        return selectedTagList.map { newTag ->
            checkAvailableTagUseCase.invoke(newTag.name)?.id ?: run {
                saveTagUseCase.invoke(newTag)
            }
        }
    }
}