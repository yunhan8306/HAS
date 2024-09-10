package com.myStash.android

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myStash.android.core.data.usecase.gender.GetSelectedGenderUseCase
import com.myStash.android.core.data.usecase.has.SaveHasUseCase
import com.myStash.android.core.data.usecase.init.GetInitUseCase
import com.myStash.android.core.data.usecase.init.SetInitUseCase
import com.myStash.android.core.data.usecase.tag.CheckAvailableTagUseCase
import com.myStash.android.core.data.usecase.tag.GetTagListUseCase
import com.myStash.android.core.data.usecase.tag.SaveTagUseCase
import com.myStash.android.core.data.usecase.type.CheckAvailableTypeUseCase
import com.myStash.android.core.data.usecase.type.GetTypeListUseCase
import com.myStash.android.core.data.usecase.type.SaveTypeUseCase
import com.myStash.android.core.di.DefaultDispatcher
import com.myStash.android.core.di.IoDispatcher
import com.myStash.android.core.model.Has
import com.myStash.android.core.model.testManTypeTotalList
import com.myStash.android.core.model.testTagList
import com.myStash.android.feature.gallery.ImageRepository
import com.myStash.android.feature.gender.GenderType
import com.myStash.android.feature.gender.getGenderType
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
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getSelectedGenderUseCase: GetSelectedGenderUseCase,
    private val getInitUseCase: GetInitUseCase,
    private val setInitUseCase: SetInitUseCase,

    // test
    private val saveTagUseCase: SaveTagUseCase,
    private val checkAvailableTagUseCase: CheckAvailableTagUseCase,
    private val saveTypeUseCase: SaveTypeUseCase,
    private val checkAvailableTypeUseCase: CheckAvailableTypeUseCase,
    private val saveHasUseCase: SaveHasUseCase,

    private val getTagListUseCase: GetTagListUseCase,
    private val getTypeListUseCase: GetTypeListUseCase,

    private val imageRepository: ImageRepository,
    // dispatcher
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
): ViewModel(), ContainerHost<MainState, MainSideEffect> {

    override val container: Container<MainState, MainSideEffect> = container(MainState.Init)

    private val genderState = getSelectedGenderUseCase.gender
        .map { it.getGenderType() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = GenderType.NONE
        )

    init {
//        fetch()
        testInit()
    }

    private fun fetch() {
        intent {
            viewModelScope.launch {
                genderState.collectLatest { gender ->
                    if(gender == GenderType.UNSELECTED) {
                        postSideEffect(MainSideEffect.StartGenderActivity)
                    }
                }
            }
        }
    }

    /** test */
    private fun testInit() {
        viewModelScope.launch(defaultDispatcher) {
            getInitUseCase.init.collectLatest { isInit ->
                Log.d("qwe123", "isInit - $isInit")
                if(!isInit) {
                    testSaveTagList()
                    testSaveTypeList()
                    testSaveHasList()
//                    setInitUseCase.invoke(true)
                }
            }
        }
    }

    private suspend fun testSaveTagList() {
        testTagList.forEach { tag ->
            val saveTagData = checkAvailableTagUseCase.invoke(tag.name)
            if(saveTagData == null) {
                saveTagUseCase.invoke(tag)
            }
        }
    }

    private suspend fun testSaveTypeList() {
        testManTypeTotalList.forEach { type ->
            val saveTypeData = checkAvailableTypeUseCase.invoke(type.name)
            if(saveTypeData == null) {
                saveTypeUseCase.invoke(type)
            }
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

    private suspend fun testSaveHasList() {
        val testHastCnt = 9
        combine(typeTotalList, tagTotalList, imageRepository.imagesStateFlow) { typeList, tagList, imageList ->
            Triple(typeList, tagList, imageList)
        }.collectLatest { (typeList, tagList, imageList) ->
            if(typeList.isNotEmpty() && tagList.isNotEmpty() && imageList.isNotEmpty() && imageList.size >= testHastCnt) {
                (0 until testHastCnt).forEachIndexed { index, i ->
                    val typeIndex = (testHastCnt - index) % testManTypeTotalList.size
                    val saveTypeId = typeIndex.takeIf { it != -1 }?.let { typeList[typeIndex].id }!!
                    val saveTagIds = tagList.filter { tagList.indexOf(it) % 10 == index }.map { it.id!! }
                    val saveHas = Has(
                        imagePath = imageList[index].uri.toString(),
                        type = saveTypeId,
                        tags = saveTagIds
                    )
                    val saveHasId = saveHasUseCase.invoke(saveHas)
                }
                setInitUseCase.invoke(true)
            }
        }
    }
}