package com.has.android.data.database.datasource.type

import com.has.android.data.database.dao.type.TypeDao
import com.has.android.data.database.model.TypeEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TypeDataSourceImpl @Inject constructor(
    private val typeDao: TypeDao
) : TypeDataSource {
    override suspend fun insert(entity: TypeEntity): Long =
        typeDao.insert(entity)

    override suspend fun update(entity: TypeEntity): Int =
        typeDao.update(entity)

    override suspend fun delete(entity: TypeEntity): Int =
        typeDao.delete(entity)

    override fun selectAll(): Flow<List<TypeEntity>> =
        typeDao.selectAll()

    override suspend fun getType(name: String): TypeEntity? =
        typeDao.getType(name)
}