package com.myStash.core.data.repository.tag

import com.myStash.core.model.Tag
import kotlinx.coroutines.flow.Flow

interface TagRepository {
    suspend fun insert(tag: Tag): Long
    suspend fun update(tag: Tag): Int
    suspend fun delete(tag: Tag): Int
    fun selectAll() : Flow<List<Tag>>
}