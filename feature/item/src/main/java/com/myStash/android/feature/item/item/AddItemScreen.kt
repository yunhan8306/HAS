package com.myStash.android.feature.item.item

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.TabRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import com.myStash.android.design_system.ui.DevicePreviews
import com.myStash.android.design_system.ui.component.header.HasHeader
import com.myStash.android.design_system.ui.component.tab.AddItemTab
import com.myStash.android.design_system.util.addFocusCleaner

@Composable
fun AddItemScreen(
    state: AddItemState,
    onSelectTab: (ItemTab) -> Unit,
    onBack: () -> Unit,
    content: @Composable () -> Unit
) {
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .height(80.dp)
            .fillMaxWidth()
            .background(Color.White)
//            .addFocusCleaner(focusManager)
    ) {
        HasHeader(
            text = "등록하기",
            onBack = onBack
        )
        TabRow(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp),
            selectedTabIndex = state.selectedTab.ordinal,
            contentColor = Color(0xFF202020)
        ) {
            AddItemTab(
                name = ItemTab.HAS.tabName,
                selected = state.selectedTab == ItemTab.HAS,
                onClick = { onSelectTab.invoke(ItemTab.HAS) }
            )
            AddItemTab(
                name = ItemTab.STYLE.tabName,
                selected = state.selectedTab == ItemTab.STYLE,
                onClick = { onSelectTab.invoke(ItemTab.STYLE) }
            )
            AddItemTab(
                name = ItemTab.FEED.tabName,
                selected = state.selectedTab == ItemTab.FEED,
                onClick = { onSelectTab.invoke(ItemTab.FEED) }
            )
        }
    }
    content.invoke()
}

@DevicePreviews
@Composable
fun AddItemScreenPreview() {
    AddItemScreen(
        state = AddItemState(),
        onSelectTab = {},
        onBack = {},
        content = {}
    )
}