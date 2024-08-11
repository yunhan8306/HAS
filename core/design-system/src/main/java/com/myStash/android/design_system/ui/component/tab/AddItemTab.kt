package com.myStash.android.design_system.ui.component.tab

import androidx.compose.foundation.background
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Tab
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.myStash.android.design_system.ui.color.ColorFamilyBlackAndWhite
import com.myStash.android.design_system.ui.color.ColorFamilyGray200AndGray600
import com.myStash.android.design_system.ui.color.Gray350
import com.myStash.android.design_system.ui.component.text.HasFontWeight
import com.myStash.android.design_system.ui.component.text.HasText

@Composable
fun AddItemTab(
    name: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Tab(
        modifier = Modifier.background(MaterialTheme.colors.surface),
        selected = selected,
//        selectedContentColor = ColorFamilyBlackAndWhite,
//        unselectedContentColor = ColorFamilyGray200AndGray600,
        onClick = onClick
    ) {
        HasText(
            text = name,
            color = if(selected) ColorFamilyBlackAndWhite else Gray350,
            fontWeight = if(selected) HasFontWeight.Bold else HasFontWeight.Medium
        )
    }
}