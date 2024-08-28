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
import com.myStash.android.design_system.ui.color.ColorFamilyWhiteAndGray800
import com.myStash.android.design_system.ui.color.Gray600
import com.myStash.android.design_system.ui.color.Gray800
import com.myStash.android.design_system.ui.color.Lime
import com.myStash.android.design_system.ui.color.White

private val LightColorScheme = darkColors(
    primary = Black,
    secondary = Lime,
    background = White,
    surface = White
)

private val DarkColorScheme = lightColors(
    primary = Lime,
    secondary = Black,
    background = Black,
    surface = Gray600
)

private val SearchDarkColorScheme = lightColors(
    primary = Lime,
    secondary = Black,
    background = Gray800,
    surface = Gray800
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

@Composable
fun HasMoreTheme(
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
        systemUiController.setStatusBarColor(colors.surface)
        systemUiController.setNavigationBarColor(navigationColor)
    }

    MaterialTheme(
        colors = colors,
//        typography = Typography,
        content = content
    )
}

@Composable
fun HasSearchTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        SearchDarkColorScheme
    } else {
        LightColorScheme
    }

    val systemUiController = rememberSystemUiController()
    val navigationColor = ColorFamilyWhiteAndGray800

    SideEffect {
        systemUiController.setStatusBarColor(colors.surface)
        systemUiController.setNavigationBarColor(navigationColor)
    }

    MaterialTheme(
        colors = colors,
//        typography = Typography,
        content = content
    )
}

@Composable
fun HasSplashTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        SearchDarkColorScheme
    } else {
        LightColorScheme
    }

    val systemUiController = rememberSystemUiController()
    val navigationColor = ColorFamilyWhiteAndGray800

    SideEffect {
        systemUiController.setStatusBarColor(colors.surface)
        systemUiController.setNavigationBarColor(navigationColor)
    }

    MaterialTheme(
        colors = colors,
//        typography = Typography,
        content = content
    )
}