package com.myStash.android.core.data.usecase.feed

import com.myStash.android.core.data.repository.feed.FeedRepository
import com.myStash.android.core.data.repository.has.HasRepository
import com.myStash.android.core.model.Feed
import com.myStash.android.core.model.Has
import javax.inject.Inject

class UpdateFeedUseCase @Inject constructor(
    private val feedRepository: FeedRepository
) {
    suspend fun invoke(feed: Feed) =
        feedRepository.update(feed)
}