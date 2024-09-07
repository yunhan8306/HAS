package com.myStash.android.feature.myPage

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myStash.android.common.util.getAppVersion
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val application: Application,
): ViewModel(), ContainerHost<MyPageScreenState, MyPageSideEffect> {
    override val container: Container<MyPageScreenState, MyPageSideEffect> =
        container(MyPageScreenState())

    init {
        fetch()
    }

    private fun fetch() {
        /**
         * todo
         * 1. profile image save, load
         * 2. nick name save, load, edit
         * 3. version update
         * */

        Log.d("qwe123", "application.getAppVersion() - ${application.getAppVersion()}")
        intent {
            viewModelScope.launch {
                reduce {
                    state.copy(
                        version = application.getAppVersion()
                    )
                }
            }
        }
    }

    fun onAction(action: MyPageScreenAction) {
        viewModelScope.launch {
            when(action) {
                is MyPageScreenAction.SelectProfile -> {
                    selectProfile(action.uri)
                }
                else -> Unit
            }
        }
    }

    private fun selectProfile(uri: String) {
        intent {
            viewModelScope.launch {
                reduce {
                    state.copy(
                        profile = uri
                    )
                }
            }
        }
    }
}