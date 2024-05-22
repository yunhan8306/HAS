package com.myStash.android.feature.item

import androidx.compose.foundation.text2.input.TextFieldState
import androidx.compose.foundation.text2.input.textAsFlow
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myStash.android.core.data.usecase.item.SaveItemUseCase
import com.myStash.android.core.data.usecase.tag.CheckAvailableTagUseCase
import com.myStash.android.core.data.usecase.tag.GetTagListUseCase
import com.myStash.android.core.data.usecase.tag.SaveTagUseCase
import com.myStash.android.core.model.Item
import com.myStash.android.core.model.Tag
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.debounce
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
class ItemViewModel @Inject constructor(
    private val checkAvailableTagUseCase: CheckAvailableTagUseCase,

    private val getTagListUseCase: GetTagListUseCase,

    private val saveItemUseCase: SaveItemUseCase,
    private val saveTagUseCase: SaveTagUseCase,
    private val stateHandle: SavedStateHandle,
): ContainerHost<ItemScreenState, ItemSideEffect>, ViewModel() {
    override val container: Container<ItemScreenState, ItemSideEffect> =
        container(ItemScreenState())

    init {
        fetch()
    }

    val selectedTagList = mutableListOf<Tag>()

    val newTagList = mutableListOf<String>()

    val addTagState = TextFieldState()

    val addTagStateFlow = addTagState.textAsFlow()
        .debounce(500L)
        .onEach {

        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = ""
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

                reduce {
                    state.copy(
                        imageUri = item?.imagePath,
                        type = "",
                        brand = item?.getBrand(tagTotalList.value),
                        tagList = item?.getTagList(tagTotalList.value) ?: emptyList(),
                    )
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

    fun addTag(name: String) {
        intent {
            selectedTagList.add(Tag(name = name))
            reduce {
                state.copy(
                    tagList = selectedTagList.toList()
                )
            }
        }
    }

    fun removeTag(name: String) {
        intent {
            selectedTagList.removeIf { it.name == name }
            reduce {
                state.copy(
                    tagList = selectedTagList.toList()
                )
            }
        }
    }

    fun addBrand(name: String) {
        intent {
            val brand = Tag(name = name, isBrand = true)
            state.brand?.let {
                selectedTagList.indexOfFirst { it.isBrand }.takeIf { it != -1 }?.let { index ->
                    selectedTagList.removeAt(index)
                }
            }
            selectedTagList.add(brand)

            reduce {
                state.copy(
                    brand = brand
                )
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


                val newItem = Item(
                    name = "",
                    tags = tagIdList,
                    brand = state.brand?.id,
                    type = "",
                    imagePath = state.imageUri,
                )

                saveItemUseCase.invoke(newItem)

                postSideEffect(ItemSideEffect.Finish)
            }
        }
    }
}