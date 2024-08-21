package com.myStash.android.feature.manage.tag

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import org.orbitmvi.orbit.compose.collectAsState

@Composable
fun ManageTagRoute(
    viewModel: ManageTagViewModel = hiltViewModel(),
) {
    val state by viewModel.collectAsState()

    ManageTagScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
fun ManageTagScreen(
    state: ManageTagState,
    onAction: (ManageTagAction) -> Unit
) {

}