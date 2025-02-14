package com.has.android.data.database.dao.tag

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.has.android.data.database.dao.base.BaseDao
import com.has.android.data.database.model.TagEntity
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
    suspend fun getTag(name: String): TagEntity?

}