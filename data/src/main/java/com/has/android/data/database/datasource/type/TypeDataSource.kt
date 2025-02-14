package com.has.android.data.database.datasource.type

import com.has.android.data.database.model.TypeEntity
import kotlinx.coroutines.flow.Flow

interface TypeDataSource {
    suspend fun insert(entity: TypeEntity): Long
    suspend fun update(entity: TypeEntity): Int
    suspend fun delete(entity: TypeEntity): Int
    fun selectAll() : Flow<List<TypeEntity>>
    suspend fun getType(name: String): TypeEntity?
}