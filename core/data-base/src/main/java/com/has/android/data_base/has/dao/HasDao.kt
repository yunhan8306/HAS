package com.has.android.data_base.has.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.has.android.data_base.BaseDao
import com.has.android.data_base.has.entity.HasEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HasDao : BaseDao<HasEntity> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override suspend fun insert(entity: HasEntity): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    override suspend fun update(entity: HasEntity): Int

    @Delete
    override suspend fun delete(entity: HasEntity): Int

    @Query("SELECT * FROM has")
    fun selectAll() : Flow<List<HasEntity>>
}