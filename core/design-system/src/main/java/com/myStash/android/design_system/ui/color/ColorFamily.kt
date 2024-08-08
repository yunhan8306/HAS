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

val ColorFamilyLimeAndBlack
    @Composable get() = ColorFamily(Lime, Black).color

val ColorFamilyGray200AndBlack
    @Composable get() = ColorFamily(Gray200, Black).color

val ColorFamilyGray200AndGray400
    @Composable get() = ColorFamily(Gray200, Gray400).color

val ColorFamilyBlackAndLime
    @Composable get() = ColorFamily(Black, Lime).color

val ColorFamilyGray300AndGray400
    @Composable get() = ColorFamily(Gray300, Gray400).color

val ColorFamilyGray500AndGray900
    @Composable get() = ColorFamily(Gray500, Gray900).color


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