package com.myStash.android.core.data.usecase.feed

import com.myStash.android.core.data.repository.feed.FeedRepository
import com.myStash.android.core.model.Feed
import javax.inject.Inject

class SaveFeedUseCase @Inject constructor(
    private val feedRepository: FeedRepository
) {
    suspend fun invoke(feed: Feed) =
        feedRepository.insert(feed)
}