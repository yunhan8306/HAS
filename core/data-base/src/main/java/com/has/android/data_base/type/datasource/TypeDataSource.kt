package com.has.android.data_base.type.datasource

import com.has.android.core.model.Type
import com.has.android.data_base.type.entity.TypeEntity
import kotlinx.coroutines.flow.Flow

interface TypeDataSource {
    suspend fun insert(entity: TypeEntity): Long
    suspend fun update(entity: TypeEntity): Int
    suspend fun delete(entity: TypeEntity): Int
    fun selectAll() : Flow<List<TypeEntity>>
    suspend fun getType(name: String): TypeEntity?
}