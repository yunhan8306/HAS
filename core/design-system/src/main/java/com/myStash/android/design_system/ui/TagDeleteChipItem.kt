package com.myStash.android.design_system.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun TagDeleteChipItem(
    name: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier.padding(end = 6.dp)
    ) {
        Box(
            modifier = Modifier
                .padding(start = 12.dp, top = 7.dp, end = 8.dp, bottom = 7.dp)
                .background(color = Color(0xFFE4F562), shape = RoundedCornerShape(size = 15.dp))
                .clickable { onClick.invoke() },
            contentAlignment = Alignment.Center
        ) {
            Row {
                Text(
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                    text = name
                )
                Spacer(modifier = Modifier.width(5.dp))
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .background(Color.Black)
                )
            }
        }
    }
}
@Composable
fun TagSelectChipItem2(
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
                    if (isSelected) Color(0xFFE4F562) else Color(0xFFF1F1F1)
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