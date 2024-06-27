package com.myStash.android.design_system.ui

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

@Composable
fun HasText(
    text: String,
    fontSize: TextUnit,
) {
    Text(
        text = text,
        style = TextStyle(
            fontSize = fontSize,
            lineHeight = 22.sp,
            fontWeight = FontWeight(500),
            color = Color(0xFF202020),
            textAlign = TextAlign.Center,
        )
    )
}