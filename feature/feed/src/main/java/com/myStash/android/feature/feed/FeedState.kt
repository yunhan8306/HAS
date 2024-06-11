package com.myStash.android.feature.feed

import java.time.LocalDate

data class FeedScreenState(
    val currentDate: LocalDate = LocalDate.now(),
    val calenderDataList: List<CalenderData> = emptyList()
)

sealed interface FeedSideEffect