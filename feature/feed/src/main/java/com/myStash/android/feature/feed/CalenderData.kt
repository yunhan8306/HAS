package com.myStash.android.feature.feed

sealed interface CalenderData {
    data class DayOfWeek(val name: String): CalenderData
    object Spacer: CalenderData
    data class Day(val day: String): CalenderData
    data class RecodedDay(
        val day: String,
        val imageUri: String,
    ): CalenderData
}