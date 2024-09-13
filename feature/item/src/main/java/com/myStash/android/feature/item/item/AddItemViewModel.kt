package com.myStash.android.feature.item.item

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myStash.android.common.util.constants.ItemConstants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class AddItemViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
): ContainerHost<AddItemState, AddItemSideEffect>, ViewModel() {
    override val container: Container<AddItemState, AddItemSideEffect> =
        container(AddItemState())

    init {
        fetch()
    }

    private fun fetch() {
        intent {
            viewModelScope.launch {
                reduce {
                    state.copy(
                        selectedTab = savedStateHandle.get<String>(ItemConstants.CMD_TAB_NAME).getItemTab(),
                        editTab = savedStateHandle.get<String>(ItemConstants.CMD_EDIT_TAB_NAME).getEditTab()
                    )
                }
            }
        }
    }

    fun selectTab(selectTab: ItemTab) {
        intent {
            viewModelScope.launch {
                reduce {
                    state.copy(selectedTab = selectTab)
                }
            }
        }
    }
}