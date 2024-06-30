package com.myStash.android.design_system.ui.tag

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.myStash.android.design_system.ui.DevicePreviews
import com.myStash.android.design_system.ui.HasText

@Composable
fun TagChipItem(
    name: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier.padding(4.dp)
    ) {
        Box(
            modifier = Modifier
                .height(28.dp)
                .background(
                    color = if (isSelected) Color(0xFFE4F562) else Color(0xFFE8E8E8),
                    shape = RoundedCornerShape(size = 15.dp)
                )
                .padding(horizontal = 12.dp)
                .clickable { onClick.invoke() },
            contentAlignment = Alignment.Center
        ) {
            HasText(
                text = name,
                fontSize = 14.dp,
            )
        }
    }
}

@DevicePreviews
@Composable
fun TagSelectChipItemPreview() {
    Row {
        TagChipItem(
            name = "test",
            isSelected = true,
            onClick = {}
        )
        TagChipItem(
            name = "test",
            isSelected = false,
            onClick = {}
        )
    }
}