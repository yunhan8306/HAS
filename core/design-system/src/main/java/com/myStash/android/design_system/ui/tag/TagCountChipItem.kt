package com.myStash.android.design_system.ui.tag

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import com.myStash.android.design_system.ui.DevicePreviews
import com.myStash.android.design_system.ui.HasFontWeight
import com.myStash.android.design_system.ui.HasText

@Composable
fun TagCountChipItem(
    name: String,
    cnt: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier.padding(4.dp)
    ) {
        Box(
            modifier = Modifier
                .height(28.dp)
                .background(color = Color(0xFFE4F562), shape = RoundedCornerShape(size = 15.dp))
                .padding(start = 12.dp, end = 8.dp)
                .clickable { onClick.invoke() },
            contentAlignment = Alignment.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                HasText(
                    modifier = Modifier.padding(end = 4.dp),
                    text = name,
                    fontSize = 14.dp,
                )
                HasText(
                    text = cnt,
                    color = Color(0xFF707070),
                    fontSize = 12.dp,
                    fontWeight = HasFontWeight.Thin
                )
            }
        }
    }
}

@DevicePreviews
@Composable
fun TagCountChipItemPreview() {
    TagCountChipItem(
        name = "ABCD",
        cnt = "+999",
        onClick = {}
    )
}