package com.has.android.feature.item.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.has.android.design_system.ui.component.text.HasFontWeight
import com.has.android.design_system.ui.component.text.HasText

@Composable
fun ItemTitleText(
    modifier: Modifier = Modifier,
    text: String,
) {
    HasText(
        modifier = modifier,
        text = text,
        fontSize = 14.dp,
        fontWeight = HasFontWeight.Bold
    )
}