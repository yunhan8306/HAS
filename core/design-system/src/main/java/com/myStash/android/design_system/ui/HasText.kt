package com.myStash.android.design_system.ui

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun HasText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color(0xFF202020),
    fontSize: Dp = 15.dp,
    fontWeight: HasFontWeight = HasFontWeight.Medium,
    textAlign: TextAlign = TextAlign.Start,
    letterSpacing: Dp = 0.dp,
    lineHeight: Dp = 0.dp,
    maxLines: Int = Int.MAX_VALUE,
) {
    val fontSizeSp = with(LocalDensity.current) { fontSize.toSp() }
    val letterSpacingSp = with(LocalDensity.current) { letterSpacing.toSp() }
    val lineHeightSp = with(LocalDensity.current) { lineHeight.toSp() }

    Text(
        text = text,
        modifier = modifier,
        style = TextStyle(
            color = color,
            fontSize = fontSizeSp,
            fontWeight = when (fontWeight) {
                HasFontWeight.Thin -> FontWeight.Normal
                HasFontWeight.Medium -> FontWeight.Medium
                HasFontWeight.Bold -> FontWeight.Bold
            },
            letterSpacing = letterSpacingSp,
            textAlign = textAlign,
            lineHeight = lineHeightSp,
            platformStyle = PlatformTextStyle(includeFontPadding = false)
        ),
        maxLines = maxLines,
    )
}

sealed interface HasFontWeight {
    object Thin : HasFontWeight
    object Medium : HasFontWeight
    object Bold : HasFontWeight
}

object TextConstant {
    const val HAS_TEXT_EMPTY = ""
}