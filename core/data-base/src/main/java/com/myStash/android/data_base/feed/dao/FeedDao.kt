package com.myStash.android.data_base.feed.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.myStash.android.data_base.BaseDao
import com.myStash.android.data_base.feed.entity.FeedEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FeedDao : BaseDao<FeedEntity> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override suspend fun insert(entity: FeedEntity): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    override suspend fun update(entity: FeedEntity): Int

    @Delete
    override suspend fun delete(entity: FeedEntity): Int

    @Query("SELECT * FROM feed")
    fun selectAll() : Flow<List<FeedEntity>>

//    fun getFeed() : FeedEntity
}