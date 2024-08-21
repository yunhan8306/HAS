package com.myStash.android.feature.manage.type

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import org.orbitmvi.orbit.compose.collectAsState

@Composable
fun ManageTypeRoute(
    viewModel: ManageTypeViewModel = hiltViewModel(),
) {
    val state by viewModel.collectAsState()

    ManageTypeScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
fun ManageTypeScreen(
    state: ManageTypeState,
    onAction: (ManageTypeAction) -> Unit
) {

}