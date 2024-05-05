package com.myStash.core.data.repository.style

import com.myStash.core.model.Style
import com.myStash.data_base.style.datasource.StyleDataSource
import com.myStash.data_base.style.entity.StyleEntity.Companion.toStyle
import com.myStash.data_base.style.entity.StyleEntity.Companion.toStyleEntity
import javax.inject.Inject

class StyleRepositoryImpl @Inject constructor(
    private val styleDataSource: StyleDataSource
) : StyleRepository {
    override suspend fun insert(style: Style): Long =
        styleDataSource.insert(style.toStyleEntity())

    override suspend fun update(style: Style): Int =
        styleDataSource.update(style.toStyleEntity())

    override suspend fun delete(style: Style): Int =
        styleDataSource.delete(style.toStyleEntity())

    override suspend fun selectAll(): List<Style> =
        styleDataSource.selectAll().map { it.toStyle() }
}