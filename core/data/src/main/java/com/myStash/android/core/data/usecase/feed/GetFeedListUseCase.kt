package com.myStash.android.core.data.usecase.feed

import com.myStash.android.core.data.repository.feed.FeedRepository
import com.myStash.android.core.model.Feed
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetFeedListUseCase @Inject constructor(
    private val feedRepository: FeedRepository
) {
    val feedList: Flow<List<Feed>> =
        feedRepository.selectAll().map { list -> list.filter { !it.isRemove } }
}