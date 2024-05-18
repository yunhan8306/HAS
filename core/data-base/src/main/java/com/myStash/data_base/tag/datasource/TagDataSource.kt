package com.myStash.data_base.tag.datasource

import com.myStash.data_base.tag.entity.TagEntity
import kotlinx.coroutines.flow.Flow

interface TagDataSource {
    suspend fun insert(entity: TagEntity): Long
    suspend fun update(entity: TagEntity): Int
    suspend fun delete(entity: TagEntity): Int
    fun selectAll() : Flow<List<TagEntity>>
    suspend fun getTag(name: String): TagEntity?
}