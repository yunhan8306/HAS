package com.myStash.android.design_system.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.myStash.android.design_system.ui.color.Black
import com.myStash.android.design_system.ui.color.ColorFamilyWhiteAndGray600
import com.myStash.android.design_system.ui.color.Lime
import com.myStash.android.design_system.ui.color.White

private val LightColorScheme = darkColors(
    primary = Black,
    secondary = Lime,
    background = White,
//    surface = Gray900
)

private val DarkColorScheme = lightColors(
    primary = Lime,
    secondary = Black,
    background = Black,
//    surface = Gray100
)

@Composable
fun HasDefaultTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }

    val systemUiController = rememberSystemUiController()
    val navigationColor = ColorFamilyWhiteAndGray600

    SideEffect {
        systemUiController.setStatusBarColor(colors.background)
        systemUiController.setNavigationBarColor(navigationColor)
    }

    MaterialTheme(
        colors = colors,
//        typography = Typography,
        content = content
    )
}