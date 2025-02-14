package com.has.android.feature.item.item

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TabRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import com.has.android.design_system.ui.DevicePreviews
import com.has.android.design_system.ui.color.ColorFamilyBlack20AndWhite
import com.has.android.design_system.ui.component.header.HasHeader
import com.has.android.design_system.ui.component.tab.AddItemTab

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
            .background(MaterialTheme.colors.surface)
            .height(92.dp)
            .fillMaxWidth()
//            .addFocusCleaner(focusManager)
    ) {
        HasHeader(
            text = if(state.selectedTab.tabName == state.editTab?.tabName) "수정하기" else "등록하기",
            onBack = onBack
        )
        TabRow(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp),
            selectedTabIndex = state.selectedTab.ordinal,
            contentColor = ColorFamilyBlack20AndWhite
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