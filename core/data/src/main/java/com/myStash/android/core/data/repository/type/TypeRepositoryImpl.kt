package com.myStash.android.core.data.repository.type

import com.myStash.android.core.model.Has
import com.myStash.android.core.model.Type
import com.myStash.android.data_base.has.datasource.HasDataSource
import com.myStash.android.data_base.has.entity.HasEntity.Companion.toHas
import com.myStash.android.data_base.has.entity.HasEntity.Companion.toHasEntity
import com.myStash.android.data_base.type.datasource.TypeDataSource
import com.myStash.android.data_base.type.entity.TypeEntity.Companion.toEntity
import com.myStash.android.data_base.type.entity.TypeEntity.Companion.toType
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
}