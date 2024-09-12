package com.myStach.android.feature.contact

import android.app.Application
import android.content.Intent
import androidx.compose.foundation.text2.input.TextFieldState
import androidx.compose.foundation.text2.input.clearText
import androidx.compose.foundation.text2.input.textAsFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myStash.android.common.util.checkEmail
import com.myStash.android.common.util.getUri
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
    private val application: Application,
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
                Intent(Intent.ACTION_SEND_MULTIPLE).apply {
                    type = "message/rfc822"
                    putExtra(Intent.EXTRA_EMAIL, arrayOf("dbsgks17@gamil.com"))
                    putExtra(Intent.EXTRA_SUBJECT, state.selectedType)
                    putExtra(Intent.EXTRA_TEXT, "receive email : ${emailTextState.text}\n"
                            + state.content.replace("\n", ""))

                    val imageUris = state.selectedImages.map { it.getUri(application) }
                    putParcelableArrayListExtra(Intent.EXTRA_STREAM, ArrayList(imageUris))
                    addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

                    postSideEffect(ContactSideEffect.SendEmail(this))
                }
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
                    if(state.isCompleted != (checkContactType && checkContent && checkEmail)) {
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