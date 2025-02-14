package com.has.android.data_base.feed.datasource

import com.has.android.data_base.feed.dao.FeedDao
import com.has.android.data_base.feed.entity.FeedEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FeedDataSourceImpl @Inject constructor(
    private val feedDao: FeedDao
) : FeedDataSource {
    override suspend fun insert(entity: FeedEntity): Long =
        feedDao.insert(entity)

    override suspend fun update(entity: FeedEntity): Int =
        feedDao.update(entity)

    override suspend fun delete(entity: FeedEntity): Int =
        feedDao.delete(entity)

    override fun selectAll(): Flow<List<FeedEntity>> =
        feedDao.selectAll()

//    override fun getFeed(): FeedEntity =
//        feedDao.getFeed()
}