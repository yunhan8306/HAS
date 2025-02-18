package com.has.android.feature.item.item

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.has.android.feature.item.feed.AddFeedRoute
import com.has.android.feature.item.has.AddHasRoute
import com.has.android.feature.item.style.AddStyleRoute
import org.orbitmvi.orbit.compose.collectAsState

@Composable
fun AddItemRoute(
    viewModel: AddItemViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    val state by viewModel.collectAsState()

    AddItemScreen(
        state = state,
        onSelectTab = viewModel::selectTab,
        onBack = onBack,
        content = {
            when(state.selectedTab) {
                ItemTab.HAS -> {
                    AddHasRoute()
                }
                ItemTab.STYLE -> {
                    AddStyleRoute()
                }
                ItemTab.FEED -> {
                    AddFeedRoute()
                }
            }
        }
    )
}