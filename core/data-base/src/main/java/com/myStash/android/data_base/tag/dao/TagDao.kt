package com.myStash.android.data_base.tag.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.myStash.android.data_base.BaseDao
import com.myStash.android.data_base.tag.entity.TagEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TagDao: BaseDao<TagEntity> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override suspend fun insert(entity: TagEntity): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    override suspend fun update(entity: TagEntity): Int

    @Delete
    override suspend fun delete(entity: TagEntity): Int

    @Query("SELECT * FROM tag")
    fun selectAll() : Flow<List<TagEntity>>

    @Query("SELECT * FROM tag WHERE name = :name LIMIT 1")
    fun getTag(name: String): TagEntity?

}