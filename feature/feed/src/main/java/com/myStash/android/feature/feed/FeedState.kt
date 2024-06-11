package com.myStash.android.feature.feed

import java.time.LocalDate

data class FeedScreenState(
    val calenderDate: LocalDate = LocalDate.now(),
    val calenderDataList: List<CalenderData> = emptyList(),
    val selectDate: LocalDate = LocalDate.now()
)

sealed interface FeedSideEffect