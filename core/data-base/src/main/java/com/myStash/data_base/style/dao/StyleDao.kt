package com.myStash.data_base.style.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.myStash.data_base.BaseDao
import com.myStash.data_base.style.entity.StyleEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface StyleDao : BaseDao<StyleEntity> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override suspend fun insert(entity: StyleEntity): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    override suspend fun update(entity: StyleEntity): Int

    @Delete
    override suspend fun delete(entity: StyleEntity): Int

    @Query("SELECT * FROM style")
    fun selectAll() : Flow<List<StyleEntity>>
}