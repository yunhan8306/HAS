package com.has.android.data.database.datasource.tag

import kotlinx.coroutines.flow.Flow

interface TagDataSource {
    suspend fun insert(entity: com.has.android.data.database.model.TagEntity): Long
    suspend fun update(entity: com.has.android.data.database.model.TagEntity): Int
    suspend fun delete(entity: com.has.android.data.database.model.TagEntity): Int
    fun selectAll() : Flow<List<com.has.android.data.database.model.TagEntity>>
    suspend fun getTag(name: String): com.has.android.data.database.model.TagEntity?
}