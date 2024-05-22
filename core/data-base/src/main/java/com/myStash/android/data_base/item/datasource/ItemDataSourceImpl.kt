package com.myStash.android.data_base.item.datasource

import com.myStash.android.data_base.item.dao.ItemDao
import com.myStash.android.data_base.item.entity.ItemEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ItemDataSourceImpl @Inject constructor(
    private val itemDao: ItemDao
) : ItemDataSource {
    override suspend fun insert(entity: ItemEntity): Long =
        itemDao.insert(entity)

    override suspend fun update(entity: ItemEntity): Int =
        itemDao.update(entity)

    override suspend fun delete(entity: ItemEntity): Int =
        itemDao.delete(entity)

    override fun selectAll(): Flow<List<ItemEntity>> =
        itemDao.selectAll()

}