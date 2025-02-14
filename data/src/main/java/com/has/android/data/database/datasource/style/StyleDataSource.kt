package com.has.android.data.database.datasource.style

import com.has.android.data.database.model.StyleEntity
import kotlinx.coroutines.flow.Flow

interface StyleDataSource {
    suspend fun insert(entity: StyleEntity): Long
    suspend fun update(entity: StyleEntity): Int
    suspend fun delete(entity: StyleEntity): Int
    fun selectAll(): Flow<List<StyleEntity>>
}