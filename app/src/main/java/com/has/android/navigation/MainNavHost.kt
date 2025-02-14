package com.has.android.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.has.android.feature.essential.ui.hasScreen
import com.has.android.feature.feed.ui.feedScreen
import com.has.android.feature.myPage.ui.myPageScreen
import com.has.android.feature.style.ui.styleScreen

@Composable
fun MainNavHost(
    navHostController: NavHostController,
) {
    AnimatedNavHost(
        navController = navHostController,
        startDestination = MainNavType.HAS.name
    ) {
        hasScreen()
        styleScreen()
        feedScreen()
        myPageScreen()
    }
}