package com.myStash.android.feature.feed

data class FeedScreenState(
    val calenderDataList: List<CalenderData> = emptyList()
)

sealed interface FeedSideEffect