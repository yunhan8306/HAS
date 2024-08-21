package com.myStash.android.feature.manage.tag

import androidx.compose.foundation.text2.input.TextFieldState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myStash.android.core.data.usecase.tag.GetTagListUseCase
import com.myStash.android.core.data.usecase.tag.SaveTagUseCase
import com.myStash.android.core.data.usecase.tag.UpdateTagUseCase
import com.myStash.android.core.model.Tag
import com.myStash.android.core.model.update
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class ManageTagViewModel @Inject constructor(
    private val getTagListUseCase: GetTagListUseCase,
    private val updateTagUseCase: UpdateTagUseCase,
    private val saveTagUseCase: SaveTagUseCase,
): ContainerHost<ManageTagState, ManageTagSideEffect>, ViewModel() {
    override val container: Container<ManageTagState, ManageTagSideEffect> =
        container(ManageTagState())

    val searchTextState = TextFieldState()

    private val tagTotalList = getTagListUseCase.tagList
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = emptyList()
        )

    init {
        fetch()
    }

    private fun fetch() {
        intent {
            viewModelScope.launch {
                tagTotalList.collectLatest { tagList ->
                    reduce {
                        state.copy(
                            tagTotalList = tagList,
                        )
                    }
                }
            }
        }
    }

    fun onAction(action: ManageTagAction) {
        viewModelScope.launch {
            when(action) {
                is ManageTagAction.AddTag -> {
                    addType()
                }
                is ManageTagAction.RemoveTag -> {
                     removeType(action.tag)
                }
                is ManageTagAction.UpdateTag -> {
                    updateType(action.tag)
                }
            }
        }
    }

    private fun addType() {
        viewModelScope.launch {
            val newTag = Tag(
                name = searchTextState.text.toString()
            )

            val id = saveTagUseCase.invoke(newTag)
        }
    }

    private fun removeType(tag: Tag) {
        intent {
            viewModelScope.launch {
                val removeTag = tag.copy(isRemove = true)
                updateTagUseCase.invoke(removeTag)

                reduce {
                    state.copy(
                        tagTotalList = state.tagTotalList.update(removeTag)
                    )
                }
            }
        }
    }

    private fun updateType(tag: Tag) {
        intent {
            viewModelScope.launch {
                updateTagUseCase.invoke(tag)

                reduce {
                    state.copy(
                        tagTotalList = state.tagTotalList.update(tag)
                    )

                }
            }
        }
    }

}

data class ManageTagState(
    val tagTotalList: List<Tag> = emptyList()
)

sealed interface ManageTagSideEffect {

}

sealed interface ManageTagAction {
    object AddTag: ManageTagAction
    data class RemoveTag(val tag: Tag): ManageTagAction
    data class UpdateTag(val tag: Tag): ManageTagAction
}