package com.has.android.data.database.repository.feed

import com.has.android.domain.database.database.repository.feed.FeedRepository
import com.has.android.core.model.Feed
import com.has.android.data.database.datasource.feed.FeedDataSource
import com.has.android.data.database.model.FeedEntity.Companion.toEntity
import com.has.android.data.database.model.FeedEntity.Companion.toFeed
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FeedRepositoryImpl @Inject constructor(
    private val feedDataSource: FeedDataSource
) : FeedRepository {
    override suspend fun insert(feed: Feed): Long =
        feedDataSource.insert(feed.toEntity())
    override suspend fun update(feed: Feed): Int =
        feedDataSource.update(feed.toEntity())
    override suspend fun delete(feed: Feed): Int =
        feedDataSource.delete(feed.toEntity())
    override fun selectAll() : Flow<List<Feed>> =
        feedDataSource.selectAll().map { list -> list.map { it.toFeed() } }
//    override suspend fun getFeed(): Feed? =
//        feedDataSource.getFeed()?.toFeed()
}