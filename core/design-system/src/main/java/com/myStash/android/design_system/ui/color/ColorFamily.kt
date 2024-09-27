package com.myStash.android.design_system.ui.color

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

data class ColorFamily constructor(
    private val lightColor: Color,
    private val darkColor: Color
) {
    val color: Color @Composable get() = if (isSystemInDarkTheme()) darkColor else lightColor
}

val ColorFamilyWhiteAndGray600
    @Composable get() = ColorFamily(White, Gray600).color

val ColorFamilyGray200AndGray600
    @Composable get() = ColorFamily(Gray200, Gray600).color

val ColorFamilyBlack20AndWhite
    @Composable get() = ColorFamily(Black20, White).color

val ColorFamilyWhiteAndBlack20
    @Composable get() = ColorFamily(White, Black20).color

val ColorFamilyLime100AndBlack20
    @Composable get() = ColorFamily(Lime100, Black20).color

val ColorFamilyGray200AndBlack20
    @Composable get() = ColorFamily(Gray200, Black20).color

val ColorFamilyGray200AndGray400
    @Composable get() = ColorFamily(Gray200, Gray400).color

val ColorFamilyGray300AndGray400
    @Composable get() = ColorFamily(Gray300, Gray400).color

val ColorFamilyGray500AndGray900
    @Composable get() = ColorFamily(Gray500, Gray900).color

val ColorFamilyLime100AndGray550
    @Composable get() = ColorFamily(Lime100, Gray550).color

val ColorFamilyLime700AndLime300
    @Composable get() = ColorFamily(Lime700, Lime300).color

val ColorFamilyGray900AndGray400
    @Composable get() = ColorFamily(Gray900, Gray400).color

val ColorFamilyGray300AndGray600
    @Composable get() = ColorFamily(Gray300, Gray600).color

val ColorFamilyWhiteAndGray800
    @Composable get() = ColorFamily(White, Gray800).color

val ColorGray50AndGray550
    @Composable get() = ColorFamily(Gray50, Gray550).color

val ColorFamilyBlack20AndLime300
    @Composable get() = ColorFamily(Black20, Lime300).color

val ColorFamilyLime300AndBlack20
    @Composable get() = ColorFamily(Lime300, Black20).color

val ColorFamilyWhiteAndGray400
    @Composable get() = ColorFamily(White, Gray400).color

val ColorFamilyBlack20AndGray450
    @Composable get() = ColorFamily(Black20, Gray450).color

val ColorFamilyLime100AndGray600
    @Composable get() = ColorFamily(Lime100, Gray600).color

val ColorFamilyLime500AndLime300
    @Composable get() = ColorFamily(Lime500, Lime300).color

val ColorFamilyLime700AndLime200
    @Composable get() = ColorFamily(Lime700, Lime200).color

val ColorFamilyGray100AndGray800
    @Composable get() = ColorFamily(Gray100, Gray800).color

val ColorFamilyLime700AndGray400
    @Composable get() = ColorFamily(Lime700, Gray400).color

val ColorFamilyGray350AndGray400
    @Composable get() = ColorFamily(Gray350, Gray400).color

val ColorFamilyGray300AndGray500
    @Composable get() = ColorFamily(Gray300, Gray500).color