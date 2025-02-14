package com.has.android.feature.item.style

import android.app.Activity
import androidx.activity.ComponentActivity
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun AddStyleRoute(
    viewModel: AddStyleViewModel = hiltViewModel()
) {
    val activity = LocalContext.current as ComponentActivity
    val state by viewModel.collectAsState()
    val searchModalState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden, skipHalfExpanded = true)
    val scope = rememberCoroutineScope()

    viewModel.collectSideEffect { sideEffect ->
        when(sideEffect) {
            is AddStyleSideEffect.Finish -> {
                activity.apply {
                    setResult(Activity.RESULT_OK, sideEffect.intent)
                    finish()
                }
            }
            else -> Unit
        }
    }

    AddStyleScreen(
        state = state,
        onAction = { action ->
            when(action) {
                else -> viewModel.onAction(action)
            }
        },
        sheetContent = {
            AddStyleModalSheet(
                searchModalState = searchModalState,
                searchTextState = viewModel.searchTextState,
                state = state,
                onAction = { action ->
                    when(action) {
                        else -> viewModel.onAction(action)
                    }
                }
            )
        }
    )
}