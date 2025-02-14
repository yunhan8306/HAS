package com.has.android.feature.manage.manage

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.has.android.feature.manage.tag.ManageTagAction
import com.has.android.feature.manage.tag.ManageTagRoute
import com.has.android.feature.manage.tag.ManageTagViewModel
import com.has.android.feature.manage.type.ManageTypeAction
import com.has.android.feature.manage.type.ManageTypeRoute
import com.has.android.feature.manage.type.ManageTypeViewModel
import org.orbitmvi.orbit.compose.collectAsState

@Composable
fun ManageRoute(
    viewModel: ManageViewModel = hiltViewModel(),
    manageTypeViewModel: ManageTypeViewModel = hiltViewModel(),
    manageTagViewModel: ManageTagViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    val state by viewModel.collectAsState()

    LaunchedEffect(state.selectedTab) {
        when(state.selectedTab) {
            ItemTab.TYPE -> {
                manageTagViewModel.onAction(ManageTagAction.FocusTag(null))
            }
            ItemTab.TAG -> {
                manageTypeViewModel.onAction(ManageTypeAction.FocusType(null))
            }
        }
    }

    ManageScreen(
        state = state,
        onSelectTab = viewModel::selectTab,
        onBack = onBack,
        content = {
            when(state.selectedTab) {
                ItemTab.TYPE -> {
                    ManageTypeRoute()
                }
                ItemTab.TAG -> {
                    ManageTagRoute()
                }
            }
        },
    )
}