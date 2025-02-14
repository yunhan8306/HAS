package com.has.android.feature.gender

enum class GenderType {
    MAN, WOMAN, NONE, UNSELECTED
}

fun String.getGenderType(): GenderType {
    return GenderType.values().firstOrNull { it.name == this } ?: GenderType.NONE
}