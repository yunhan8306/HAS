package com.has.android.core.data.usecase.feed

import com.has.android.core.data.repository.feed.FeedRepository
import com.has.android.core.model.Feed
import javax.inject.Inject

class SaveFeedUseCase @Inject constructor(
    private val feedRepository: FeedRepository
) {
    suspend fun invoke(feed: Feed) =
        feedRepository.insert(feed)
}