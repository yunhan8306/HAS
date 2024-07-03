package com.myStash.android.design_system.ui.component.type

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
import com.myStash.android.design_system.ui.DevicePreviews

@Composable
fun TypeChipItem(
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
                    if(isSelected) Color(0xFF202020) else Color(0xFFF1F1F1)
                )
                .clickable { onClick.invoke() },
            contentAlignment = Alignment.Center
        ) {
            Text(
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                text = name,
                color = if(isSelected) Color(0xFFF1F1F1) else Color(0xFF707070)
            )
        }
    }
}

@DevicePreviews
@Composable
fun TypeChipItemPreview() {
    TypeChipItem(
        name = "test",
        isSelected = false,
        onClick = {}
    )
}