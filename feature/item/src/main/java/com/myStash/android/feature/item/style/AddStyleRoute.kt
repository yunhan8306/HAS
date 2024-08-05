package com.myStash.android.feature.item.style

import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun AddStyleRoute(
    viewModel: AddStyleViewModel = hiltViewModel(),
    finishActivity: () -> Unit
) {
    val state by viewModel.collectAsState()
    val searchModalState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden, skipHalfExpanded = true)
    val scope = rememberCoroutineScope()

    viewModel.collectSideEffect { sideEffect ->
        when(sideEffect) {
            AddStyleSideEffect.Finish -> finishActivity.invoke()
            else -> Unit
        }
    }

    AddStyleScreen(
        selectedHasList = state.selectedHasList,
        saveItem = viewModel::saveStyle,
        sheetContent = {
            AddStyleModalSheet(
                searchModalState = searchModalState,
                searchTextState = viewModel.searchTextState,
                state = state,
                onSelectType = viewModel::selectType,
                onSelectHas = viewModel::selectHas,
            )
        }
    )
}