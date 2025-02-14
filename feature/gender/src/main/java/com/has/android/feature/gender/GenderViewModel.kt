package com.has.android.feature.gender

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.has.android.core.data.usecase.gender.GetSelectedGenderUseCase
import com.has.android.core.data.usecase.gender.SaveGenderUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class GenderViewModel @Inject constructor(
    private val saveGenderUseCase: SaveGenderUseCase,
    private val getSelectedGenderUseCase: GetSelectedGenderUseCase,
): ViewModel(), ContainerHost<GenderScreenState, GenderSideEffect> {

    override val container: Container<GenderScreenState, GenderSideEffect> =
        container(GenderScreenState.Init)

    init {
        fetch()
    }
    private fun fetch() {
        intent {
            viewModelScope.launch {
                val saveGenderType = getSelectedGenderUseCase.gender.first().getGenderType()
                reduce {
                    state.copy(
                        gender = saveGenderType
                    )
                }
            }
        }
    }

    fun selectGender(genderType: GenderType) {
        intent {
            viewModelScope.launch {
                reduce {
                    state.copy(
                        gender = genderType
                    )
                }
            }
        }
    }

    fun saveGender() {
        intent {
            viewModelScope.launch {
                saveGenderUseCase.invoke(state.gender.name)
                postSideEffect(GenderSideEffect.Finish)
            }
        }
    }
}