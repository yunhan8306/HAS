package com.has.android.design_system.ui.component.type

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.has.android.design_system.ui.color.DimBackground60
import com.has.android.design_system.ui.color.White
import com.has.android.design_system.ui.component.text.HasFontWeight
import com.has.android.design_system.ui.component.text.HasText
import com.has.android.design_system.ui.theme.clickableNoRipple

@Composable
fun TypeSquareChipItem(
    name: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .height(22.dp)
            .clip(shape = RoundedCornerShape(size = 6.dp))
            .background(DimBackground60)
            .padding(horizontal = 8.dp)
            .clickableNoRipple { onClick.invoke() },
        contentAlignment = Alignment.Center
    ) {
        HasText(
            text = name,
            color = White,
            fontSize = 12.dp,
            fontWeight = HasFontWeight.Bold
        )
    }
}