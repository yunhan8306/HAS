package com.myStash.android.feature.item

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ItemContentRow(
    modifier: Modifier = Modifier,
    borderColor: Color = Color(0xFF202020),
    content: @Composable () -> Unit,
    endContent: @Composable (() -> Unit)? = null
) {
    Row(
        modifier = modifier
            .border(width = 1.dp, color = borderColor, shape = RoundedCornerShape(size = 10.dp))
            .fillMaxWidth()
            .height(44.dp)
            .background(color = Color(0xFFFFFFFF), shape = RoundedCornerShape(size = 10.dp)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        content.invoke()
        endContent?.let {
            Spacer(modifier = Modifier.weight(1f))
            endContent.invoke()
        }
    }
}