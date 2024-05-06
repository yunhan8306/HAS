package com.myStash.core.data.repository.style

import com.myStash.core.model.Style
import com.myStash.data_base.style.entity.StyleEntity

interface StyleRepository {
    suspend fun insert(style: Style): Long
    suspend fun update(style: Style): Int
    suspend fun delete(style: Style): Int
    suspend fun selectAll(): List<Style>
}