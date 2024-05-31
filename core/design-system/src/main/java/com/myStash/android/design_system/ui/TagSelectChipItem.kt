package com.myStash.android.design_system.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun TagSelectChipItem(
    name: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier.padding(5.dp)
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .background(
                    if(isSelected) Color(0xFFE4F562) else Color(0xFFF1F1F1)
                )
                .clickable { onClick.invoke() },
            contentAlignment = Alignment.Center
        ) {
            Text(
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                text = name
            )
        }
    }
}

@DevicePreviews
@Composable
fun TagSelectChipItemPreview() {
    TagSelectChipItem(
        name = "test",
        isSelected = true,
        onClick = {}
    )
}