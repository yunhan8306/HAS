package com.myStash.core.data.repository.tag

import com.myStash.core.model.Tag
import com.myStash.data_base.tag.entity.TagEntity
import kotlinx.coroutines.flow.Flow

interface TagRepository {
    suspend fun insert(tag: Tag): Long
    suspend fun update(tag: Tag): Int
    suspend fun delete(tag: Tag): Int
    fun selectAll() : Flow<List<Tag>>
    suspend fun getTag(name: String): Tag?
}