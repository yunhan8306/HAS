package com.has.android.data_base.has.datasource

import com.has.android.data_base.has.entity.HasEntity
import kotlinx.coroutines.flow.Flow

interface HasDataSource {
    suspend fun insert(entity: HasEntity): Long
    suspend fun update(entity: HasEntity): Int
    suspend fun delete(entity: HasEntity): Int
    fun selectAll() : Flow<List<HasEntity>>
}