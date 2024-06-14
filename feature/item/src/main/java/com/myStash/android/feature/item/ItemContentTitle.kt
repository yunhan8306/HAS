package com.myStash.android.feature.item

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun ItemContentTitle(
    modifier: Modifier = Modifier,
    text: String,
) {
    Text(
        modifier = modifier,
        text = text,
        style = TextStyle(
            fontSize = 14.sp,
            lineHeight = 14.sp,
            fontWeight = FontWeight(700),
            color = Color(0xFF000000),
        )
    )
}