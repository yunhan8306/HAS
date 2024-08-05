package com.myStash.android.feature.item.item

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.myStash.android.feature.item.feed.AddFeedRoute
import com.myStash.android.feature.item.has.AddHasRoute
import com.myStash.android.feature.item.style.AddStyleRoute
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
                    AddHasRoute(finishActivity = onBack)
                }
                ItemTab.STYLE -> {
                    AddStyleRoute(finishActivity = onBack)
                }
                ItemTab.FEED -> {
                    AddFeedRoute(finishActivity = onBack)
                }
            }
        }
    )
}