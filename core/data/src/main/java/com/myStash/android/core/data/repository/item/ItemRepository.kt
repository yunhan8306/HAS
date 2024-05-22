package com.myStash.android.core.data.repository.item

import com.myStash.android.core.model.Item
import kotlinx.coroutines.flow.Flow

interface ItemRepository {
    suspend fun insert(item: Item): Long
    suspend fun update(item: Item): Int
    suspend fun delete(item: Item): Int
    fun selectAll() : Flow<List<Item>>
}