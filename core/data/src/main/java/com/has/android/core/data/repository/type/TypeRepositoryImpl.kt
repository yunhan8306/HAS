package com.has.android.core.data.repository.type

import com.has.android.core.model.Has
import com.has.android.core.model.Type
import com.has.android.data_base.has.datasource.HasDataSource
import com.has.android.data_base.has.entity.HasEntity.Companion.toHas
import com.has.android.data_base.has.entity.HasEntity.Companion.toHasEntity
import com.has.android.data_base.type.datasource.TypeDataSource
import com.has.android.data_base.type.entity.TypeEntity
import com.has.android.data_base.type.entity.TypeEntity.Companion.toEntity
import com.has.android.data_base.type.entity.TypeEntity.Companion.toType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TypeRepositoryImpl @Inject constructor(
    private val typeDataSource: TypeDataSource
) : TypeRepository {
    override suspend fun insert(type: Type): Long =
        typeDataSource.insert(type.toEntity())
    override suspend fun update(type: Type): Int =
        typeDataSource.update(type.toEntity())
    override suspend fun delete(type: Type): Int =
        typeDataSource.delete(type.toEntity())
    override fun selectAll() : Flow<List<Type>> =
        typeDataSource.selectAll().map { list -> list.map { it.toType() } }
    override suspend fun getType(name: String): Type? =
        typeDataSource.getType(name)?.toType()
}