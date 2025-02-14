package com.has.android.domain.database.database.repository.has

import com.has.android.core.model.Has
import kotlinx.coroutines.flow.Flow

interface HasRepository {
    suspend fun insert(has: Has): Long
    suspend fun update(has: Has): Int
    suspend fun delete(has: Has): Int
    fun selectAll() : Flow<List<Has>>
}