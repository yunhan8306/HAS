package com.myStash.android

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.myStash.android.feature.essential.hasScreen
import com.myStash.android.feature.feed.feedScreen
import com.myStash.android.feature.myPage.myPageScreen
import com.myStash.android.feature.style.styleScreen
import com.myStash.android.navigation.MainNavType

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