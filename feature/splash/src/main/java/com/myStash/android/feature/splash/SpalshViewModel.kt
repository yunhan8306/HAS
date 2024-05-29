package com.myStash.android.feature.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(

) : ViewModel(), ContainerHost<SplashScreenState, SplashSideEffect> {

    override val container: Container<SplashScreenState, SplashSideEffect> =
        container(SplashScreenState())

    init {
        initTest()
    }

    private fun initTest() {
        intent {
            viewModelScope.launch {
                while (state.testCount > 0) {
                    delay(1000)
                    reduce {
                        state.copy(testCount = state.testCount - 1)
                    }
                }
                postSideEffect(SplashSideEffect.StartMainActivity)
            }
        }
    }

    fun skip() {
        intent {
            viewModelScope.launch {
                postSideEffect(SplashSideEffect.StartMainActivity)
            }
        }
    }
}