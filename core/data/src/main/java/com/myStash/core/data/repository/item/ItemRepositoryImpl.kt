package com.myStash.core.data.repository.item

import com.myStash.core.model.Item
import com.myStash.data_base.item.datasource.ItemDataSource
import com.myStash.data_base.item.entity.ItemEntity.Companion.toItem
import com.myStash.data_base.item.entity.ItemEntity.Companion.toItemEntity
import javax.inject.Inject

class ItemRepositoryImpl @Inject constructor(
    private val itemDataSource: ItemDataSource
) : ItemRepository {
    override suspend fun insert(item: Item): Long =
        itemDataSource.insert(item.toItemEntity())
    override suspend fun update(item: Item): Int =
        itemDataSource.update(item.toItemEntity())
    override suspend fun delete(item: Item): Int =
        itemDataSource.delete(item.toItemEntity())
    override suspend fun selectAll() : List<Item> =
        itemDataSource.selectAll().map { it.toItem() }
}