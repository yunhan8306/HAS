package com.myStash.data_base.tag.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.myStash.data_base.BaseDao
import com.myStash.data_base.tag.entity.TagEntity

@Dao
interface TagDao: BaseDao<TagEntity> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override suspend fun insert(entity: TagEntity): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    override suspend fun update(entity: TagEntity): Int

    @Delete
    override suspend fun delete(entity: TagEntity): Int

    @Query("SELECT * FROM tag")
    suspend fun selectAll() : List<TagEntity>

}