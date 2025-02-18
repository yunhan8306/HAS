package com.has.android.design_system.ui.component.button

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.has.android.design_system.ui.color.Gray350
import com.has.android.design_system.ui.color.Purple
import com.has.android.design_system.ui.color.White
import com.has.android.design_system.ui.component.text.HasText

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
                .clip(shape = RoundedCornerShape(size = 10.dp))
                .border(width = 1.dp, color = Gray350, shape = RoundedCornerShape(size = 10.dp))
                .background(MaterialTheme.colors.surface)
                .width(120.dp)
                .height(48.dp)
                .clickable { onCancel.invoke() },
            contentAlignment = Alignment.Center
        ) {
            HasText(
                text = "취소",
                fontSize = 16.dp
            )
        }
        Box(modifier = Modifier.width(8.dp))
        Box(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(size = 10.dp))
                .background(Purple)
                .weight(1f)
                .height(48.dp)
                .clickable { onSelect.invoke() },
            contentAlignment = Alignment.Center
        ) {
            HasText(
                text = selectText,
                color = White,
                fontSize = 16.dp
            )
        }
    }
}