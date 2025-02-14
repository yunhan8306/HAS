package com.has.android.core.data.repository.tag

import com.has.android.core.model.Tag
import com.has.android.data_base.tag.entity.TagEntity
import kotlinx.coroutines.flow.Flow

interface TagRepository {
    suspend fun insert(tag: Tag): Long
    suspend fun update(tag: Tag): Int
    suspend fun delete(tag: Tag): Int
    fun selectAll() : Flow<List<Tag>>
    suspend fun getTag(name: String): Tag?
}