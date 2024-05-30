package com.myStash.android

data class MainState(
    val status: MainStatus = MainStatus.UnInit
) {
    companion object {
        val Init: MainState = MainState()
    }
}

sealed interface MainStatus {
    object UnInit : MainStatus
    object Success : MainStatus
}

sealed interface MainSideEffect {
    object StartGenderActivity: MainSideEffect
}