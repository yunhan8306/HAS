package com.myStash.data_base.style.datasource

import com.myStash.data_base.style.entity.StyleEntity
import kotlinx.coroutines.flow.Flow

interface StyleDataSource {
    suspend fun insert(entity: StyleEntity): Long
    suspend fun update(entity: StyleEntity): Int
    suspend fun delete(entity: StyleEntity): Int
    fun selectAll(): Flow<List<StyleEntity>>
}