package com.has.android.data_base.type.datasource

import com.has.android.data_base.type.dao.TypeDao
import com.has.android.data_base.type.entity.TypeEntity
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