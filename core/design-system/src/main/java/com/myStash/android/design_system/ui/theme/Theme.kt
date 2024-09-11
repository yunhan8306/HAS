package com.myStash.android.design_system.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.myStash.android.design_system.ui.color.Black20
import com.myStash.android.design_system.ui.color.ColorFamilyWhiteAndGray600
import com.myStash.android.design_system.ui.color.ColorFamilyWhiteAndGray800
import com.myStash.android.design_system.ui.color.Gray600
import com.myStash.android.design_system.ui.color.Gray800
import com.myStash.android.design_system.ui.color.Lime
import com.myStash.android.design_system.ui.color.White

private val LightColorScheme = darkColors(
    primary = Black20,
    secondary = Lime,
    background = White,
    surface = White
)

private val DarkColorScheme = lightColors(
    primary = Lime,
    secondary = Black20,
    background = Black20,
    surface = Gray600
)

private val DarkColorSchemeSecond = lightColors(
    primary = Lime,
    secondary = Black20,
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
        DarkColorSchemeSecond
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
        DarkColorSchemeSecond
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
fun HasGalleryTheme(
    content: @Composable () -> Unit
) {
    val colors = DarkColorScheme

    val systemUiController = rememberSystemUiController()

    SideEffect {
        systemUiController.setStatusBarColor(Color.Black)
        systemUiController.setNavigationBarColor(Color.Black)
    }

    MaterialTheme(
        colors = colors,
//        typography = Typography,
        content = content
    )
}