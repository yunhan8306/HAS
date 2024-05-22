package com.myStash.android.core.data.repository.item

import com.myStash.android.core.model.Item
import com.myStash.android.data_base.item.datasource.ItemDataSource
import com.myStash.android.data_base.item.entity.ItemEntity.Companion.toItem
import com.myStash.android.data_base.item.entity.ItemEntity.Companion.toItemEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
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
    override fun selectAll() : Flow<List<Item>> =
        itemDataSource.selectAll().map { list -> list.map { it.toItem() } }
}