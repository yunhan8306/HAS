package com.has.android.core.data.usecase.feed

import com.has.android.core.data.repository.feed.FeedRepository
import com.has.android.core.data.repository.has.HasRepository
import com.has.android.core.model.Feed
import com.has.android.core.model.Has
import javax.inject.Inject

class DeleteFeedUseCase @Inject constructor(
    private val feedRepository: FeedRepository
) {
    suspend fun invoke(feed: Feed) =
        feedRepository.update(feed.copy(isRemove = true))
}