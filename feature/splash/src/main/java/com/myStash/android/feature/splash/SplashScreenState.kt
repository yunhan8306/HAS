package com.myStash.android.feature.splash

data class SplashScreenState(
   val testCount: Int = 3
)

sealed interface SplashSideEffect {
    object StartMainActivity: SplashSideEffect
}