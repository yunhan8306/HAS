package com.myStash.android.feature.manage.manage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TabRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import com.myStash.android.design_system.ui.color.ColorFamilyBlackAndWhite
import com.myStash.android.design_system.ui.component.header.HasHeader
import com.myStash.android.design_system.ui.component.tab.AddItemTab
import com.myStash.android.design_system.util.addFocusCleaner

@Composable
fun ManageScreen(
    state: ManageScreenState,
    onSelectTab: (ItemTab) -> Unit,
    onBack: () -> Unit,
    content: @Composable () -> Unit
) {

    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .background(MaterialTheme.colors.surface)
            .height(80.dp)
            .fillMaxWidth()
            .addFocusCleaner(focusManager)
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
            contentColor = ColorFamilyBlackAndWhite
        ) {
            AddItemTab(
                name = ItemTab.TYPE.tabName,
                selected = state.selectedTab == ItemTab.TYPE,
                onClick = { onSelectTab.invoke(ItemTab.TYPE) }
            )
            AddItemTab(
                name = ItemTab.TAG.tabName,
                selected = state.selectedTab == ItemTab.TAG,
                onClick = { onSelectTab.invoke(ItemTab.TAG) }
            )
        }
    }
    content.invoke()
}