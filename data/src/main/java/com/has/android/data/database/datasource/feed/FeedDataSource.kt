package com.has.android.data.database.datasource.feed

import com.has.android.data.database.model.FeedEntity
import kotlinx.coroutines.flow.Flow

interface FeedDataSource {
    suspend fun insert(entity: FeedEntity): Long
    suspend fun update(entity: FeedEntity): Int
    suspend fun delete(entity: FeedEntity): Int
    fun selectAll(): Flow<List<FeedEntity>>
//    fun getFeed(): FeedEntity?
}