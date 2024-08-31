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

val ColorFamilyBlackAndWhite
    @Composable get() = ColorFamily(Black, White).color

val ColorFamilyWhiteAndBlack
    @Composable get() = ColorFamily(White, Black).color

val ColorFamilyLime100AndBlack
    @Composable get() = ColorFamily(Lime100, Black).color

val ColorFamilyGray200AndBlack
    @Composable get() = ColorFamily(Gray200, Black).color

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

val ColorFamilyBlackAndLime300
    @Composable get() = ColorFamily(Black, Lime300).color

val ColorFamilyLime300AndBlack
    @Composable get() = ColorFamily(Lime300, Black).color

val ColorFamilyWhiteAndGray400
    @Composable get() = ColorFamily(White, Gray400).color

val ColorFamilyBlackAndGray450
    @Composable get() = ColorFamily(Black, Gray450).color

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



val ColorFamilyBackground
    @Composable get() = ColorFamily(White, Gray800)

val ColorFamilyHeaderBackground
    @Composable get() = ColorFamily(White, Gray600)

val ColorFamilyBottomSheetBackground
    @Composable get() = ColorFamily(White, Gray800)

val ColorFamilyCategoryTipBackground
    @Composable get() = ColorFamily(Gray400, Gray200)

val ColorFamilyInputBoxBackground
    @Composable get() = ColorFamily(Gray200, Gray600)

val ColorFamilyInputDefaultLine
    @Composable get() = ColorFamily(Gray300, Gray500)

val ColorFamilyInputFocusLine
    @Composable get() = ColorFamily(Gray700, White)

val ColorFamilyInputBackground
    @Composable get() = ColorFamily(White, Gray700)

val ColorFamilyPlaceholder
    @Composable get() = ColorFamily(Gray400, Gray500)

val ColorFamilyText
    @Composable get() = ColorFamily(Gray700, White)

val ColorFamilySecondaryText
    @Composable get() = ColorFamily(Gray500, Gray400)

val ColorFamilyAccentText
    @Composable get() = ColorFamily(Lime300, Lime300)

val ColorFamilyLine
    @Composable get() = ColorFamily(Gray200, Gray600)

val ColorFamilySecondaryLine
    @Composable get() = ColorFamily(Gray300, Gray400)

val ColorFamilyTagBackground
    @Composable get() = ColorFamily(Gray200, Gray500)

val ColorFamilyTagText
    @Composable get() = ColorFamily(Gray700, Color(0xFFC8C8C8))

val ColorFamilyDimBackground
    @Composable get() = ColorFamily(DimBackground40, DimBackground60)

val ColorFamilyButtonActiveBackground
    @Composable get() = ColorFamily(Gray700, Lime300)

val ColorFamilyButtonActiveText
    @Composable get() = ColorFamily(Lime300, Gray700)

val ColorFamilyButtonDisabledBackground
    @Composable get() = ColorFamily(ButtonDisabledGray, Gray600)

val ColorFamilyButtonDisabledText
    @Composable get() = ColorFamily(White, Gray400)

val ColorFamilyToastBackground
    @Composable get() = ColorFamily(TagGray, Gray500)