package com.has.android.core.data.repository.style

import com.has.android.core.model.Style
import kotlinx.coroutines.flow.Flow

interface StyleRepository {
    suspend fun insert(style: Style): Long
    suspend fun update(style: Style): Int
    suspend fun delete(style: Style): Int
    fun selectAll(): Flow<List<Style>>
}