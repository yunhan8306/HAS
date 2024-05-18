package com.myStash.data_base.item.datasource

import com.myStash.data_base.item.entity.ItemEntity
import kotlinx.coroutines.flow.Flow

interface ItemDataSource {
    suspend fun insert(entity: ItemEntity): Long
    suspend fun update(entity: ItemEntity): Int
    suspend fun delete(entity: ItemEntity): Int
    fun selectAll() : Flow<List<ItemEntity>>
}