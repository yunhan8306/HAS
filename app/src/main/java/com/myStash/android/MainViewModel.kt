package com.myStash.android

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myStash.android.core.data.usecase.gender.GetSelectedGenderUseCase
import com.myStash.android.core.data.usecase.init.GetInitUseCase
import com.myStash.android.core.data.usecase.init.SetInitUseCase
import com.myStash.android.core.data.usecase.tag.CheckAvailableTagUseCase
import com.myStash.android.core.data.usecase.tag.SaveTagUseCase
import com.myStash.android.core.model.testTagList
import com.myStash.android.feature.gender.GenderType
import com.myStash.android.feature.gender.getGenderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
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
    private val checkAvailableTagUseCase: CheckAvailableTagUseCase
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

    private fun testInit() {
        viewModelScope.launch {
            getInitUseCase.init.collectLatest { isInit ->
                if(!isInit) {
                    testSaveTagList()
                    setInitUseCase.invoke(true)
                }
            }
        }
    }

    private suspend fun testSaveTagList() {
        viewModelScope.launch {
            testTagList.forEach { tag ->
                val saveTagData = checkAvailableTagUseCase.invoke(tag.name)
                if(saveTagData == null) {
                    saveTagUseCase.invoke(tag)
                }
            }
        }
    }
}