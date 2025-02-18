package com.has.android.feature.splash.ui

data class SplashScreenState(
    val status: SplashStatus = SplashStatus.Loading
)

sealed interface SplashSideEffect {
    object StartMainActivity: SplashSideEffect
}

sealed interface SplashStatus {
    object Loading : SplashStatus
    object Success : SplashStatus
    object Finish : SplashStatus
    data class Fail(val error: Throwable?) : SplashStatus
}