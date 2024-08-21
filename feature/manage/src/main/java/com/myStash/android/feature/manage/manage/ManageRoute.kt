package com.myStash.android.feature.manage.manage

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.myStash.android.feature.manage.tag.ManageTagRoute
import com.myStash.android.feature.manage.type.ManageTypeRoute
import org.orbitmvi.orbit.compose.collectAsState

@Composable
fun ManageRoute(
    viewModel: ManageViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    val state by viewModel.collectAsState()

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