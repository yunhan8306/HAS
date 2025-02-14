package com.has.android.design_system.ui.component.tag

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.has.android.design_system.ui.DevicePreviews
import com.has.android.design_system.ui.component.text.HasText

@Composable
fun TagHasChipItem(
    name: String,
    isSelected: Boolean
) {
    Box(
        modifier = Modifier.padding(start = 2.dp, top = 3.dp, end = 2.dp, bottom = 3.dp),
    ) {
        Box(
            modifier = Modifier
                .height(20.dp)
                .background(
                    color = if(isSelected) Color(0x4DFFFFFF) else Color(0x99000000),
                    shape = RoundedCornerShape(size = 15.dp)
                )
                .padding(start = 6.dp, end = 6.dp),
            contentAlignment = Alignment.Center
        ) {
            HasText(
                text = name,
                color = if(isSelected) Color(0xFFFFFFFF) else Color(0xB2FFFFFF),
                fontSize = 12.dp,
            )
        }
    }
}

@DevicePreviews
@Composable
fun TagHasChipItemPreview() {
    Row {
        TagHasChipItem(
            name = "test",
            isSelected = true
        )
        TagHasChipItem(
            name = "test",
            isSelected = false
        )
    }
}