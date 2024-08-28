package com.myStash.android.feature.manage.tag

import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.myStash.android.common.util.isNotNull
import org.orbitmvi.orbit.compose.collectAsState

@Composable
fun ManageTagRoute(
    viewModel: ManageTagViewModel = hiltViewModel(),
) {
    val activity = LocalContext.current as ComponentActivity
    val state by viewModel.collectAsState()

    ManageTagScreen(
        state = state,
        addTagTextState = viewModel.addTagTextState,
        onAction = viewModel::onAction
    )

    BackHandler {
        when {
            state.focusTag.isNotNull() -> {
                viewModel.onAction(ManageTagAction.FocusTag(null))
            }
            else -> {
                activity.finish()
            }
        }
    }
}