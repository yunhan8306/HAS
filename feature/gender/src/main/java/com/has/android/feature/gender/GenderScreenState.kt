package com.has.android.feature.gender

data class GenderScreenState(
    val gender: GenderType = GenderType.NONE
) {
    companion object {
        val Init: GenderScreenState = GenderScreenState()
    }
}

sealed interface GenderSideEffect {
    object Finish : GenderSideEffect
}