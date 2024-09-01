package com.myStash.android.feature.feed

import com.myStash.android.core.model.CalenderData
import com.myStash.android.core.model.Feed
import com.myStash.android.core.model.StyleScreenModel
import com.myStash.android.core.model.Tag
import com.myStash.android.core.model.Type
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
    data class SelectDay(val date: LocalDate): FeedScreenAction
}