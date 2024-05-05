package com.myStash.data_base.style.datasource

import com.myStash.data_base.style.entity.StyleEntity

interface StyleDataSource {
    suspend fun insert(entity: StyleEntity): Long
    suspend fun update(entity: StyleEntity): Int
    suspend fun delete(entity: StyleEntity): Int
    suspend fun selectAll(): List<StyleEntity>
}