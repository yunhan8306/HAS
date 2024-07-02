package com.myStash.android.design_system.ui.content

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.myStash.android.design_system.ui.DevicePreviews
import com.myStash.android.design_system.ui.HasText

@Composable
fun ContentText(
    text: String,
    onClick: () -> Unit
) {

    Box(
        modifier = Modifier
            .border(width = 1.dp, color = Color(0xFFE1E1E1), shape = RoundedCornerShape(size = 10.dp))
            .fillMaxWidth()
            .height(44.dp)
            .background(color = Color(0xFFFFFFFF), shape = RoundedCornerShape(size = 10.dp))
            .padding(start = 12.dp)
            .clickable { onClick.invoke() },
        contentAlignment = Alignment.CenterStart
    ) {
        HasText(
            text = text,
            color = Color(0xFFA4A4A4)
        )
    }
}

@DevicePreviews
@Composable
fun ContentTextPreview() {
    ContentText(
        text = "Content Text",
        onClick = {}
    )
}