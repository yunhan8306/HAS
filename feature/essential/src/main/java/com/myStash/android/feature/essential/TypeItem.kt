package com.myStash.android.feature.essential

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp
import com.myStash.android.design_system.ui.color.ColorFamilyWhiteAndGray600
import com.myStash.android.design_system.ui.component.text.HasFontWeight
import com.myStash.android.design_system.ui.component.text.HasText

@Composable
fun TypeItem(
    name: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val drawLineColor = ColorFamilyWhiteAndGray600

    Box(
        modifier = Modifier
            .drawBehind {
                if (isSelected) {
                    drawLine(
                        color = drawLineColor,
                        start = Offset(x = 0f + 30, y = size.height),
                        end = Offset(x = size.width - 30, y = size.height),
                        strokeWidth = 4.dp.toPx(),
                        cap = StrokeCap.Round
                    )
                }
            }
            .padding(horizontal = 10.dp)
            .height(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .clickable { onClick.invoke() }
                .padding(4.dp)
        ) {
            HasText(
                text = name,
                fontWeight = if(isSelected) HasFontWeight.Bold else HasFontWeight.Medium
            )
        }
    }
}