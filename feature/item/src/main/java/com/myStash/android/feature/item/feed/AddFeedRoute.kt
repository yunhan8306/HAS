package com.myStash.android.feature.item.feed

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
    viewModel: AddFeedViewModel = hiltViewModel(),
    finishActivity: () -> Unit
) {
    val state by viewModel.collectAsState()
    val selectStyleModalState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden, skipHalfExpanded = true)
    val scope = rememberCoroutineScope()

    viewModel.collectSideEffect { sideEffect ->
        when(sideEffect) {
            AddFeedSideEffect.Finish -> finishActivity.invoke()
            else -> Unit
        }
    }

    AddFeedScreen(
        searchModalState = selectStyleModalState,
        state = state,
//        selectedHasList = state.selectedHasList,
//        saveItem = viewModel::saveFeed,
        showGalleryActivity = {},
        sheetContent = {
            SelectStyleModalSheet(
                selectStyleModalState = selectStyleModalState,
                state = state,
                searchTextState = viewModel.searchTextState,
//                onSelectType = viewModel::selectType,
//                onSelectHas = viewModel::selectHas,
            )
        }
    )
}