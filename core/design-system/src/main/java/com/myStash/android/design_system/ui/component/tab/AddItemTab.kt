package com.myStash.android.design_system.ui.component.tab

import androidx.compose.foundation.background
import androidx.compose.material.Tab
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.myStash.android.design_system.ui.component.text.HasFontWeight
import com.myStash.android.design_system.ui.component.text.HasText

@Composable
fun AddItemTab(
    name: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Tab(
        modifier = Modifier.background(Color.White),
        selected = selected,
        selectedContentColor = Color(0xFFF1F1F1),
        unselectedContentColor = Color(0xFFF1F1F1),
        onClick = onClick
    ) {
        HasText(
            text = name,
            fontWeight = HasFontWeight.Bold
        )
    }
}