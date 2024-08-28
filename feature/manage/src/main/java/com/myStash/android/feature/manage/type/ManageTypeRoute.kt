package com.myStash.android.feature.manage.type

import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.myStash.android.common.util.isNotNull
import org.orbitmvi.orbit.compose.collectAsState

@Composable
fun ManageTypeRoute(
    viewModel: ManageTypeViewModel = hiltViewModel(),
) {
    val activity = LocalContext.current as ComponentActivity
    val state by viewModel.collectAsState()

    ManageTypeScreen(
        state = state,
        addTypeTextState = viewModel.addTypeTextState,
        onAction = viewModel::onAction
    )

    BackHandler {
        when {
            state.focusType.isNotNull() -> {
                viewModel.onAction(ManageTypeAction.FocusType(null))
            }
            else -> {
                activity.finish()
            }
        }
    }
}