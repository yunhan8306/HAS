package com.myStash.android.design_system.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.myStash.android.core.design_system.R

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
                .height(28.dp)
                .background(color = Color(0xFFE4F562), shape = RoundedCornerShape(size = 15.dp))
                .padding(start = 12.dp, top = 7.dp, end = 8.dp, bottom = 7.dp)
                .clickable { onClick.invoke() },
            contentAlignment = Alignment.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = name,
                    style = TextStyle(
                        fontSize = 14.sp,
                        lineHeight = 14.sp,
                        fontWeight = FontWeight(500),
                        color = Color(0xFF202020),
                    )
                )
                Spacer(modifier = Modifier.width(2.dp))
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .background(Color.Black)
                )
            }
        }
    }
}