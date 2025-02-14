package com.has.android.domain.database.database.usecase.feed

import com.has.android.domain.database.database.repository.feed.FeedRepository
import com.has.android.core.model.Feed
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetFeedListUseCase @Inject constructor(
    private val feedRepository: FeedRepository
) {
    val feedList: Flow<List<Feed>> =
        feedRepository.selectAll().map { list -> list.filter { !it.isRemove } }
}