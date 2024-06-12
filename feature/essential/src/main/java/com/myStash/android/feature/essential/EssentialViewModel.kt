package com.myStash.android.feature.essential

import android.app.Application
import android.util.Log
import androidx.compose.foundation.text2.input.TextFieldState
import androidx.compose.foundation.text2.input.textAsFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myStash.android.common.util.offerOrRemove
import com.myStash.android.core.data.usecase.gender.GetSelectedGenderUseCase
import com.myStash.android.core.data.usecase.item.DeleteItemUseCase
import com.myStash.android.core.data.usecase.item.GetItemListUseCase
import com.myStash.android.core.data.usecase.item.SaveItemUseCase
import com.myStash.android.core.data.usecase.tag.DeleteTagUseCase
import com.myStash.android.core.data.usecase.tag.GetTagListUseCase
import com.myStash.android.core.data.usecase.tag.SaveTagUseCase
import com.myStash.android.core.di.DefaultDispatcher
import com.myStash.android.core.di.IoDispatcher
import com.myStash.android.core.model.Item
import com.myStash.android.core.model.Tag
import com.myStash.android.core.model.Type
import com.myStash.android.core.model.testManTypeTotalList
import com.myStash.android.core.model.testTagList
import com.myStash.android.core.model.testWomanTypeTotalList
import com.myStash.android.feature.gallery.ImageRepository
import com.myStash.android.feature.gender.GenderType
import com.myStash.android.feature.gender.getGenderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class EssentialViewModel @Inject constructor(
    private val application: Application,
    private val getItemListUseCase: GetItemListUseCase,
    private val getTagListUseCase: GetTagListUseCase,
    private val saveItemUseCase: SaveItemUseCase,
    // test
    private val saveTagUseCase: SaveTagUseCase,
    private val deleteTagUseCase: DeleteTagUseCase,
    private val deleteItemUseCase: DeleteItemUseCase,
    // image
    private val imageRepository: ImageRepository,
    // gender
    private val getSelectedGenderUseCase: GetSelectedGenderUseCase,
    // dispatcher
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ContainerHost<EssentialScreenState, EssentialSideEffect>, ViewModel() {
    override val container: Container<EssentialScreenState, EssentialSideEffect> =
        container(EssentialScreenState())

    init {
        fetch()
        collectSearchText()
        initGalleryImages()
    }

    val searchTextState = TextFieldState()
    private val searchTagList = searchTextState
        .textAsFlow()
        .debounce(500)
        .flowOn(defaultDispatcher)
        .mapLatest { search ->
            testTagList.filter { it.name.contains(search) }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = emptyList()
        )

    var testImageCnt = 0

    private val itemList = getItemListUseCase.itemList
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

    private val selectedTagList = mutableListOf<Tag>()

    private val typeTotalList = getSelectedGenderUseCase.gender
        .mapLatest {
            when(it.getGenderType()) {
                GenderType.MAN -> testManTypeTotalList
                GenderType.WOMAN -> testWomanTypeTotalList
                else -> emptyList()
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = emptyList()
        )

    private fun fetch() {
        intent {
            viewModelScope.launch {
                combine(typeTotalList, itemList, tagTotalList) { typeTotalList, itemList, tagTotalList ->
                    Triple(typeTotalList, itemList, tagTotalList)
                }.collectLatest { (typeTotalList, itemList, tagTotalList) ->
                    reduce {
                        state.copy(
                            itemList = itemList,
                            tagTotalList = tagTotalList,
                            typeTotalList = typeTotalList
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
                        state.copy(tagTotalList = it.toList())
                    }
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
                        selectTagList = selectedTagList.toList()
                    )
                }
            }
        }
    }

    fun selectType(type: Type) {
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

    private fun initGalleryImages() {
        viewModelScope.launch(ioDispatcher) {
            imageRepository.init()
        }
    }

    fun testItemAdd() {
        viewModelScope.launch {
            imageRepository.imagesStateFlow.collectLatest {
                Log.d("qwe123", "list - $it")
                if(it.isEmpty()) return@collectLatest

                val newItem = Item(
    //                tags = tags,
    //                brand = brand,
    //                type = type,
                    imagePath = it[testImageCnt].uri.toString(),
                    type = 1
                )
                val result = saveItemUseCase.invoke(newItem)
                testImageCnt++
            }
        }
    }

    fun testTagAdd() {
        viewModelScope.launch {
            val newTag = Tag(
                name = "태그 ${tagTotalList.value.size + 1}"
            )
            Log.d("qwe123", "newTag - $newTag")
            val result = saveTagUseCase.invoke(newTag)
        }
    }

    fun deleteAllTag() {
        viewModelScope.launch {
            tagTotalList.value.forEach {
                deleteTagUseCase.invoke(it)
            }
        }
    }

    fun deleteAllItem() {
        viewModelScope.launch {
            itemList.value.forEach {
                deleteItemUseCase.invoke(it)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        imageRepository.cleanup()
    }
}