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

val ColorFamilyBackground
    @Composable get() = ColorFamily(BackgroundLight, BackgroundDark)

val ColorFamilyHeaderBackground
    @Composable get() = ColorFamily(HeaderBackgroundLight, HeaderBackgroundDark)

val ColorFamilyBottomSheetBackground
    @Composable get() = ColorFamily(BottomSheetBackgroundLight, BottomSheetBackgroundDark)

val ColorFamilyCategoryTipBackground
    @Composable get() = ColorFamily(CategoryTipBackgroundLight, CategoryTipBackgroundDark)

val ColorFamilyInputBoxBackground
    @Composable get() = ColorFamily(InputBoxBackgroundLight, InputBoxBackgroundDark)

val ColorFamilyInputDefaultLine
    @Composable get() = ColorFamily(InputDefaultLineLight, InputDefaultLineDark)

val ColorFamilyInputFocusLine
    @Composable get() = ColorFamily(InputFocusLineLight, InputFocusLineDark)

val ColorFamilyInputBackground
    @Composable get() = ColorFamily(InputBackgroundLight, InputBackgroundDark)

val ColorFamilyPlaceholder
    @Composable get() = ColorFamily(PlaceholderLight, PlaceholderDark)

val ColorFamilyText
    @Composable get() = ColorFamily(TextLight, TextDark)

val ColorFamilySecondaryText
    @Composable get() = ColorFamily(SecondaryTextLight, SecondaryTextDark)

val ColorFamilyAccentText
    @Composable get() = ColorFamily(AccentTextLight, AccentTextDark)

val ColorFamilyLine
    @Composable get() = ColorFamily(LineLight, LineDark)

val ColorFamilySecondaryLine
    @Composable get() = ColorFamily(SecondaryLineLight, SecondaryLineDark)

val ColorFamilyTagBackground
    @Composable get() = ColorFamily(TagBackgroundLight, TagBackgroundDark)

val ColorFamilyTagText
    @Composable get() = ColorFamily(TagTextLight, TagTextDark)

val ColorFamilyDimBackground
    @Composable get() = ColorFamily(DimBackgroundLight, DimBackgroundDark)

val ColorFamilyButtonActiveBackground
    @Composable get() = ColorFamily(ButtonActiveBackgroundLight, ButtonActiveBackgroundDark)

val ColorFamilyButtonActiveText
    @Composable get() = ColorFamily(ButtonActiveTextLight, ButtonActiveTextDark)

val ColorFamilyButtonDisabledBackground
    @Composable get() = ColorFamily(ButtonDisabledBackgroundLight, ButtonDisabledBackgroundDark)

val ColorFamilyButtonDisabledText
    @Composable get() = ColorFamily(ButtonDisabledTextLight, ButtonDisabledTextDark)

val ColorFamilyToastBackground
    @Composable get() = ColorFamily(ToastBackgroundLight, ToastBackgroundDark)