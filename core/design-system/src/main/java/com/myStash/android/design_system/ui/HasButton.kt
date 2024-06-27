package com.myStash.android.design_system.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun HasButton(
    text: String,
    isComplete: Boolean = true,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .background(
                color = if(isComplete) Color(0xFF202020) else Color(0xFFD4D4D4),
                shape = RoundedCornerShape(size = 10.dp)
            )
            .clickable { if(isComplete) onClick.invoke() },
        contentAlignment = Alignment.Center
    ) {
        HasText(
            text = text,
            color = if(isComplete) Color(0xFFE4F562) else Color(0xFFFFFFFF),
            fontSize = 16.dp,
            fontWeight = HasFontWeight.Bold,
        )
    }
}

@DevicePreviews
@Composable
fun HasButtonPreview() {
    Column {
        HasButton(
            text = "Button",
            isComplete = true,
            onClick = {}
        )
        HasButton(
            text = "Button",
            isComplete = false,
            onClick = {}
        )
    }
}