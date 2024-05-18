package com.myStash.data_base.item.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.myStash.data_base.BaseDao
import com.myStash.data_base.item.entity.ItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao : BaseDao<ItemEntity> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override suspend fun insert(entity: ItemEntity): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    override suspend fun update(entity: ItemEntity): Int

    @Delete
    override suspend fun delete(entity: ItemEntity): Int

    @Query("SELECT * FROM item")
    fun selectAll() : Flow<List<ItemEntity>>
}