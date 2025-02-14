package com.has.android.domain.database.database.repository.feed

import com.has.android.core.model.Feed
import kotlinx.coroutines.flow.Flow

interface FeedRepository {
    suspend fun insert(feed: Feed): Long
    suspend fun update(feed: Feed): Int
    suspend fun delete(feed: Feed): Int
    fun selectAll() : Flow<List<Feed>>
//    suspend fun getFeed(): Feed?
}