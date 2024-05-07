package com.myStash.core.data.repository.style

import com.myStash.core.model.Style
import com.myStash.data_base.style.datasource.StyleDataSource
import com.myStash.data_base.style.entity.StyleEntity.Companion.toStyle
import com.myStash.data_base.style.entity.StyleEntity.Companion.toStyleEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
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

    override fun selectAll(): Flow<List<Style>> =
        styleDataSource.selectAll().map { list -> list.map { it.toStyle() } }
}