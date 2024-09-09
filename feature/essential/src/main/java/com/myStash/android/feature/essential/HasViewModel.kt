package com.myStash.android.feature.essential

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myStash.android.common.util.Quadruple
import com.myStash.android.common.util.offerOrRemove
import com.myStash.android.core.data.usecase.gender.GetSelectedGenderUseCase
import com.myStash.android.core.data.usecase.has.DeleteHasUseCase
import com.myStash.android.core.data.usecase.has.GetHasListUseCase
import com.myStash.android.core.data.usecase.has.SaveHasUseCase
import com.myStash.android.core.data.usecase.tag.DeleteTagUseCase
import com.myStash.android.core.data.usecase.tag.GetTagListUseCase
import com.myStash.android.core.data.usecase.tag.SaveTagUseCase
import com.myStash.android.core.data.usecase.type.GetTypeListUseCase
import com.myStash.android.core.di.DefaultDispatcher
import com.myStash.android.core.di.IoDispatcher
import com.myStash.android.core.model.Has
import com.myStash.android.core.model.Tag
import com.myStash.android.core.model.Type
import com.myStash.android.core.model.getSelectedTypeAndTagHasList
import com.myStash.android.core.model.getTagList
import com.myStash.android.core.model.getTotalTypeList
import com.myStash.android.core.model.getUnSelectTypeList
import com.myStash.android.core.model.sortSelectedTagList
import com.myStash.android.feature.gallery.ImageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class HasViewModel @Inject constructor(
    private val application: Application,
    private val getHasListUseCase: GetHasListUseCase,
    private val getTagListUseCase: GetTagListUseCase,
    private val saveHasUseCase: SaveHasUseCase,
    // test
    private val saveTagUseCase: SaveTagUseCase,
    private val deleteTagUseCase: DeleteTagUseCase,
    private val deleteHasUseCase: DeleteHasUseCase,
    // image
    private val imageRepository: ImageRepository,
    // gender
    private val getSelectedGenderUseCase: GetSelectedGenderUseCase,
    // type
    private val getTypeListUseCase: GetTypeListUseCase,
    // dispatcher
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ContainerHost<HasScreenState, HasSideEffect>, ViewModel() {
    override val container: Container<HasScreenState, HasSideEffect> =
        container(HasScreenState())

    init {
        fetch()
        initGalleryImages()
    }

    var testImageCnt = 0

    private val hasTotalList = getHasListUseCase.hasList
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
    private val selectedHasList = mutableListOf<Has>()

//    private val typeTotalList = getSelectedGenderUseCase.gender
//        .mapLatest {
//            when(it.getGenderType()) {
//                GenderType.MAN -> testManTypeTotalList
//                GenderType.WOMAN -> testWomanTypeTotalList
//                else -> emptyList()
//            }
//        }.stateIn(
//            scope = viewModelScope,
//            started = SharingStarted.WhileSubscribed(5_000L),
//            initialValue = emptyList()
//        )

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

    private fun fetch() {
        intent {
            viewModelScope.launch {
                combine(typeTotalList, hasTotalList, tagTotalList, typeRemoveList) { typeTotalList, itemList, tagTotalList, typeRemoveList ->
                    Quadruple(typeTotalList, itemList, tagTotalList, typeRemoveList)
                }.collectLatest { (typeTotalList, itemList, tagTotalList, _) ->
                    reduce {
                        state.copy(
                            hasList = itemList,
                            totalTagList = tagTotalList,
                            totalTypeList = typeTotalList,
                            searchTagList = tagTotalList
                        )
                    }
                }
            }
        }
    }

    fun onAction(action: HasScreenAction) {
        when(action) {
            is HasScreenAction.SelectType -> selectType(action.type)
            is HasScreenAction.SelectTag -> selectTag(action.tag)
            is HasScreenAction.SetTagList -> setTagList(action.tagIdList)
            is HasScreenAction.SelectHas -> selectHas(action.has)
            is HasScreenAction.ResetSelectHas -> resetSelectHas()
            is HasScreenAction.TagToggle -> toggleTag()
            is HasScreenAction.DeleteHas -> deleteHas(action.has)
            else -> Unit
        }
    }

    private fun selectTag(tag: Tag) {
        intent {
            viewModelScope.launch {
                selectedTagList.offerOrRemove(tag) { it.name == tag.name }

                reduce {
                    state.copy(
                        selectedTagList = selectedTagList.toList(),
                        hasList = hasTotalList.value.getSelectedTypeAndTagHasList(state.selectedType, selectedTagList, typeRemoveList.value)
                    )
                }
            }
        }
    }

    private fun setTagList(tagIdList: List<Long>) {
        intent {
            viewModelScope.launch {
                selectedTagList.apply {
                    clear()
                    addAll(tagIdList.getTagList(state.totalTagList))
                }

                reduce {
                    state.copy(
                        selectedTagList = selectedTagList.toList(),
                        hasList = hasTotalList.value.getSelectedTypeAndTagHasList(state.selectedType, selectedTagList, typeRemoveList.value)
                    )
                }
            }
        }
    }

    private fun selectType(type: Type) {
        intent {
            viewModelScope.launch {
                reduce {
                    state.copy(
                        hasList = hasTotalList.value.getSelectedTypeAndTagHasList(type, selectedTagList, typeRemoveList.value),
                        selectedType = type
                    )
                }
            }
        }
    }

    private fun selectHas(has: Has) {
        intent {
            viewModelScope.launch {
                selectedHasList.offerOrRemove(has) { it.id == has.id }
                reduce {
                    state.copy(
                        selectedHasList = selectedHasList.toList()
                    )
                }
            }
        }
    }

    private fun resetSelectHas() {
        intent {
            viewModelScope.launch {
                selectedHasList.clear()
                reduce {
                    state.copy(
                        selectedHasList = selectedHasList.toList()
                    )
                }
            }
        }
    }

    private fun toggleTag() {
        intent {
            viewModelScope.launch {
                reduce {
                    state.copy(
                        isFoldTag = !state.isFoldTag,
                        totalTagList = state.totalTagList.sortSelectedTagList(selectedTagList, state.isFoldTag),
                    )
                }
            }
        }
    }

    private fun deleteHas(has: Has?) {
        Log.d("qwe123", "has 삭제 - $has")
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

                val newHas = Has(
    //                tags = tags,
    //                brand = brand,
    //                type = type,
                    imagePath = it[testImageCnt].uri.toString(),
                    type = 1
                )
                val result = saveHasUseCase.invoke(newHas)
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
            hasTotalList.value.forEach {
                deleteHasUseCase.invoke(it)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        imageRepository.cleanup()
    }
}