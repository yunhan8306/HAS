package com.myStash.android.data_base.item.datasource

import com.myStash.android.data_base.item.entity.ItemEntity
import kotlinx.coroutines.flow.Flow

interface ItemDataSource {
    suspend fun insert(entity: ItemEntity): Long
    suspend fun update(entity: ItemEntity): Int
    suspend fun delete(entity: ItemEntity): Int
    fun selectAll() : Flow<List<ItemEntity>>
}