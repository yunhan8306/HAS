package com.myStash.android.feature.myPage

import android.app.Application
import androidx.compose.foundation.text2.input.TextFieldState
import androidx.compose.foundation.text2.input.setTextAndPlaceCursorAtEnd
import androidx.compose.foundation.text2.input.textAsFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myStash.android.common.util.getAppVersion
import com.myStash.android.common.util.ifEmptyReturnNull
import com.myStash.android.core.data.usecase.profile.GetNickNameUseCase
import com.myStash.android.core.data.usecase.profile.GetProfileUriUseCase
import com.myStash.android.core.data.usecase.profile.SetNickNameUseCase
import com.myStash.android.core.data.usecase.profile.SetProfileUriUseCase
import com.myStash.android.feature.myPage.ui.MyPageScreenAction
import com.myStash.android.feature.myPage.ui.MyPageScreenState
import com.myStash.android.feature.myPage.ui.MyPageSideEffect
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
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
    private val getNickNameUseCase: GetNickNameUseCase,
    private val setNickNameUseCase: SetNickNameUseCase,
    private val getProfileUriUseCase: GetProfileUriUseCase,
    private val setProfileUriUseCase: SetProfileUriUseCase,
): ViewModel(), ContainerHost<MyPageScreenState, MyPageSideEffect> {
    override val container: Container<MyPageScreenState, MyPageSideEffect> =
        container(MyPageScreenState())

    private val nickName = getNickNameUseCase.invoke()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = ""
        )

    private val profileUri = getProfileUriUseCase.invoke()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = ""
        )

    val nickNameState = TextFieldState()

    private val qq = nickNameState.textAsFlow()
        .onEach { editNickName(it.toString()) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = ""
        )

    init {
        fetch()
    }

    private fun fetch() {
        /**
         * todo
         * 1. profile image save, load - success
         * 2. nick name save, load, edit - success
         * 3. version update
         * */

        intent {
            viewModelScope.launch {
                combine(nickName, profileUri, qq) { nickName, profileUri, qq ->
                    Triple(nickName, profileUri, qq)
                }.collectLatest { (nickName, profileUri, _) ->
                    when {
                        nickName.isEmpty() -> editNickName("오늘은 뭐입지")
                        nickNameState.text.isEmpty() -> nickNameState.setTextAndPlaceCursorAtEnd(nickName)
                    }
                    reduce {
                        state.copy(
                            profile = profileUri.ifEmptyReturnNull(),
                            nickName = nickName,
                            version = application.getAppVersion()
                        )
                    }
                }
            }
        }
    }

    fun onAction(action: MyPageScreenAction) {
        viewModelScope.launch {
            when(action) {
                is MyPageScreenAction.EditProfileUri -> {
                    editProfileUri(action.uri)
                }
                else -> Unit
            }
        }
    }

    private fun editProfileUri(uri: String) {
        viewModelScope.launch {
            setProfileUriUseCase.invoke(uri)
        }
    }

    private fun editNickName(nickName: String) {
        viewModelScope.launch {
            setNickNameUseCase.invoke(nickName)
        }
    }
}