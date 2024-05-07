package com.myStash.core.data.repository.style

import com.myStash.core.model.Style
import com.myStash.data_base.style.entity.StyleEntity
import kotlinx.coroutines.flow.Flow

interface StyleRepository {
    suspend fun insert(style: Style): Long
    suspend fun update(style: Style): Int
    suspend fun delete(style: Style): Int
    fun selectAll(): Flow<List<Style>>
}