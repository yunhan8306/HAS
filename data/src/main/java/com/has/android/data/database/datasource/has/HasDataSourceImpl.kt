package com.has.android.data.database.datasource.has

import com.has.android.data.database.dao.has.HasDao
import com.has.android.data.database.model.HasEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HasDataSourceImpl @Inject constructor(
    private val hasDao: HasDao
) : HasDataSource {
    override suspend fun insert(entity: HasEntity): Long =
        hasDao.insert(entity)

    override suspend fun update(entity: HasEntity): Int =
        hasDao.update(entity)

    override suspend fun delete(entity: HasEntity): Int =
        hasDao.delete(entity)

    override fun selectAll(): Flow<List<HasEntity>> =
        hasDao.selectAll()

}