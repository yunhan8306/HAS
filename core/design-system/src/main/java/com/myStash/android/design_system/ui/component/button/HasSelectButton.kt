package com.myStash.android.design_system.ui.component.button

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.myStash.android.design_system.ui.component.text.HasText

@Composable
fun HasSelectButton(
    modifier: Modifier = Modifier,
    selectText: String,
    onCancel: () -> Unit,
    onSelect: () -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(),
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(size = 10.dp))
                .border(
                    width = 1.dp,
                    color = Color(0xFF909090),
                    shape = RoundedCornerShape(size = 10.dp)
                )
                .width(120.dp)
                .height(48.dp)
                .background(color = Color(0xFFFFFFFF), shape = RoundedCornerShape(size = 10.dp))
                .clickable { onCancel.invoke() },
            contentAlignment = Alignment.Center
        ) {
            HasText(
                text = "취소",
                color = Color(0xFF202020),
                fontSize = 16.dp
            )
        }
        Box(modifier = Modifier.width(8.dp))
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(size = 10.dp))
                .shadow(
                    elevation = 10.dp,
                    spotColor = Color(0x14000000),
                    ambientColor = Color(0x14000000)
                )
                .weight(1f)
                .height(48.dp)
                .background(color = Color(0xFF716DF6), shape = RoundedCornerShape(size = 10.dp))
                .clickable { onSelect.invoke() },
            contentAlignment = Alignment.Center
        ) {
            HasText(
                text = selectText,
                color = Color(0xFFFFFFFF),
                fontSize = 16.dp
            )
        }
    }
}