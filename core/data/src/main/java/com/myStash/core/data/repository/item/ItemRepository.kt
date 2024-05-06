package com.myStash.core.data.repository.item

import com.myStash.core.model.Item

interface ItemRepository {
    suspend fun insert(item: Item): Long
    suspend fun update(item: Item): Int
    suspend fun delete(item: Item): Int
    suspend fun selectAll() : List<Item>
}