package com.myStash.data_base.tag.datasource

import com.myStash.data_base.tag.entity.TagEntity

interface TagDataSource {
    suspend fun insert(entity: TagEntity): Long
    suspend fun update(entity: TagEntity): Int
    suspend fun delete(entity: TagEntity): Int
    suspend fun selectAll() : List<TagEntity>
}