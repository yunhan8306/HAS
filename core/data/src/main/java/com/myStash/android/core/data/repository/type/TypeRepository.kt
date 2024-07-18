package com.myStash.android.core.data.repository.type

import com.myStash.android.core.model.Type
import kotlinx.coroutines.flow.Flow

interface TypeRepository {
    suspend fun insert(type: Type): Long
    suspend fun update(type: Type): Int
    suspend fun delete(type: Type): Int
    fun selectAll() : Flow<List<Type>>
    suspend fun getType(name: String): Type?
}