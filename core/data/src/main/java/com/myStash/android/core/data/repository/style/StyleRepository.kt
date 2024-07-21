package com.myStash.android.core.data.repository.style

import com.myStash.android.core.model.Style
import kotlinx.coroutines.flow.Flow

interface StyleRepository {
    suspend fun insert(style: Style): Long
    suspend fun update(style: Style): Int
    suspend fun delete(style: Style): Int
    fun selectAll(): Flow<List<Style>>
}