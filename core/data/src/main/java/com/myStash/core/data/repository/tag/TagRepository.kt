package com.myStash.core.data.repository.tag

import com.myStash.core.model.Tag

interface TagRepository {
    suspend fun insert(tag: Tag): Long
    suspend fun update(tag: Tag): Int
    suspend fun delete(tag: Tag): Int
    suspend fun selectAll() : List<Tag>
}