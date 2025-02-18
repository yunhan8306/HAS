package com.has.android.feature.feed.ui

import com.has.android.core.model.CalenderData
import com.has.android.core.model.Feed
import com.has.android.core.model.StyleScreenModel
import com.has.android.core.model.Tag
import com.has.android.core.model.Type
import java.time.LocalDate

data class FeedScreenState(
    val calenderDate: LocalDate = LocalDate.now(),
    val calenderDataList: List<CalenderData> = emptyList(),
    val selectedDate: LocalDate = LocalDate.now(),
    val selectedFeed: Feed? = null,
    val selectedFeedTagList: List<Tag> = emptyList(),
    val selectedFeedStyle: StyleScreenModel? = null,
    val typeTotalList: List<Type> = emptyList(),
)

sealed interface FeedSideEffect

sealed interface FeedScreenAction {
    object PrevMonth: FeedScreenAction
    object NextMonth: FeedScreenAction
    object More: FeedScreenAction
    object Edit: FeedScreenAction
    object Delete: FeedScreenAction
    data class SelectDay(val date: LocalDate): FeedScreenAction
}