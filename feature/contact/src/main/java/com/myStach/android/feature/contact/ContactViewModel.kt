package com.myStach.android.feature.contact

import android.util.Log
import androidx.compose.foundation.text2.input.TextFieldState
import androidx.compose.foundation.text2.input.clearText
import androidx.compose.foundation.text2.input.textAsFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myStash.android.common.util.checkEmail
import com.myStash.android.common.util.isNotNull
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class ContactViewModel @Inject constructor(

): ViewModel(), ContainerHost<ContactState, ContactSideEffect> {

    override val container: Container<ContactState, ContactSideEffect> = container(ContactState())

    val contentTextState = TextFieldState()
    val emailTextState = TextFieldState()

    private val checkContactType = container.stateFlow
        .map { it.selectedType.isNotNull() }

    private val checkContent = contentTextState
        .textAsFlow()
        .map { it.length > 10 }

    private val checkEmail = emailTextState
        .textAsFlow()
        .map { it.checkEmail() }

    init {
        fetch()
        checkComplete()
    }

    fun onAction(action: ContactAction) {
        when(action) {

            is ContactAction.SelectType -> {
                selectType(action.type)
            }
            is ContactAction.DeleteEmail -> {
                deleteEmail()
            }
            is ContactAction.Confirm -> {
                confirm()
            }
            else -> Unit

        }
    }

    private fun selectType(type: String) {
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

    private fun deleteEmail() {
        emailTextState.clearText()
    }

    private fun confirm() {
        intent {
            viewModelScope.launch {
                // 완료 처리
                postSideEffect(ContactSideEffect.Finish)
            }
        }
    }

    fun addImageList(imageList: List<String>) {
        intent {
            viewModelScope.launch {
                reduce {
                    state.copy(
                        selectedImages = imageList
                    )
                }
            }
        }
    }

    private fun checkComplete() {
        intent {
            viewModelScope.launch {
                combine(checkContactType, checkContent, checkEmail) { checkContactType, checkContent, checkEmail ->
                    Triple(checkContactType, checkContent, checkEmail)
                }.collectLatest { (checkContactType, checkContent, checkEmail) ->
                    Log.d("qwe123", "state - ${state.isCompleted} / checkContactType - $checkContactType / checkContent - $checkContent / checkEmail - $checkEmail")
                    if(state.isCompleted != (checkContactType && checkContent && checkEmail)) {
                    Log.d("qwe123", "checkContactType && checkContent && checkEmail - ${checkContactType && checkContent && checkEmail}")
                        reduce {
                            state.copy(
                                isCompleted = checkContactType && checkContent && checkEmail
                            )
                        }
                    }
                }
            }
        }
    }

    private fun fetch() {
        intent {
            viewModelScope.launch {
                reduce {
                    state.copy(
                        typeList = listOf(
                            "기타/오류",
                            "항목1",
                            "항목2",
                            "항목3",
                            "항목4",
                            "항목5",
                            "항목6",
                        )
                    )
                }
            }
        }
    }
}