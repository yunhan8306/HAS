package com.myStash.android.feature.manage.type

import androidx.compose.foundation.text2.input.TextFieldState
import androidx.compose.foundation.text2.input.clearText
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myStash.android.core.data.usecase.type.GetTypeListUseCase
import com.myStash.android.core.data.usecase.type.SaveTypeUseCase
import com.myStash.android.core.data.usecase.type.UpdateTypeUseCase
import com.myStash.android.core.model.Type
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
class ManageTypeViewModel @Inject constructor(
    private val getTypeListUseCase: GetTypeListUseCase,
    private val updateTypeUseCase: UpdateTypeUseCase,
    private val saveTypeUseCase: SaveTypeUseCase,
): ContainerHost<ManageTypeState, ManageTypeSideEffect>, ViewModel() {
    override val container: Container<ManageTypeState, ManageTypeSideEffect> =
        container(ManageTypeState())

    val addTypeTextState = TextFieldState()

    private val typeTotalList = getTypeListUseCase.typeList
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
                typeTotalList.collectLatest { typeList ->
                    reduce {
                        state.copy(
                            typeTotalList = typeList,
                        )
                    }
                }
            }
        }
    }

    fun onAction(action: ManageTypeAction) {
        viewModelScope.launch {
            when(action) {
                is ManageTypeAction.AddType -> {
                    addType()
                }
                is ManageTypeAction.RemoveType -> {
                    removeType(action.type)
                }
                is ManageTypeAction.UpdateType -> {
                    updateType(action.type)
                }
                is ManageTypeAction.DeleteAddTypeText -> {
                    deleteAddTypeText()
                }
            }
        }
    }

    private fun addType() {
        viewModelScope.launch {
            val newType = Type(
                name = addTypeTextState.text.toString()
            )

            val id = saveTypeUseCase.invoke(newType)
        }
    }

    private fun removeType(type: Type) {
        intent {
            viewModelScope.launch {
                val removeType = type.copy(isRemove = true)
                updateTypeUseCase.invoke(removeType)

                reduce {
                    state.copy(
                        typeTotalList = state.typeTotalList.update(removeType)
                    )
                }
            }
        }
    }

    private fun updateType(type: Type) {
        intent {
            viewModelScope.launch {
                updateTypeUseCase.invoke(type)

                reduce {
                    state.copy(
                        typeTotalList = state.typeTotalList.update(type)
                    )

                }
            }
        }
    }

    private fun deleteAddTypeText() {
        addTypeTextState.clearText()
    }

}

data class ManageTypeState(
    val typeTotalList: List<Type> = emptyList()
)

sealed interface ManageTypeSideEffect {

}

sealed interface ManageTypeAction {
    data class RemoveType(val type: Type): ManageTypeAction
    data class UpdateType(val type: Type): ManageTypeAction
    object AddType: ManageTypeAction
    object DeleteAddTypeText: ManageTypeAction
}