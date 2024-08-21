package com.myStash.android.feature.manage.manage

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class ManageViewModel @Inject constructor(
    private val stateHandle: SavedStateHandle,
): ContainerHost<ManageScreenState, ManageSideEffect>, ViewModel() {
    override val container: Container<ManageScreenState, ManageSideEffect> =
        container(ManageScreenState())


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

enum class ItemTab(
    val tabName: String
) {
    TYPE("Categories"),
    TAG("Tags")
}

data class ManageScreenState(
    val selectedTab: ItemTab = ItemTab.TYPE
)

sealed interface ManageSideEffect {

}