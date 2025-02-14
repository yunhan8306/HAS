package com.has.android.domain.database.database.repository.tag

import com.has.android.core.model.Tag
import kotlinx.coroutines.flow.Flow

interface TagRepository {
    suspend fun insert(tag: Tag): Long
    suspend fun update(tag: Tag): Int
    suspend fun delete(tag: Tag): Int
    fun selectAll() : Flow<List<Tag>>
    suspend fun getTag(name: String): Tag?
}