package com.has.android.data.database.datasource.has

import com.has.android.data.database.model.HasEntity
import kotlinx.coroutines.flow.Flow

interface HasDataSource {
    suspend fun insert(entity: HasEntity): Long
    suspend fun update(entity: HasEntity): Int
    suspend fun delete(entity: HasEntity): Int
    fun selectAll() : Flow<List<HasEntity>>
}