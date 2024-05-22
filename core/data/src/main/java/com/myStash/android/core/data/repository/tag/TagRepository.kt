package com.myStash.android.core.data.repository.tag

import com.myStash.android.core.model.Tag
import com.myStash.android.data_base.tag.entity.TagEntity
import kotlinx.coroutines.flow.Flow

interface TagRepository {
    suspend fun insert(tag: Tag): Long
    suspend fun update(tag: Tag): Int
    suspend fun delete(tag: Tag): Int
    fun selectAll() : Flow<List<Tag>>
    suspend fun getTag(name: String): Tag?
}