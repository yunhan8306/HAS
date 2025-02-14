package com.has.android.data.database.repository.style

import com.has.android.domain.database.database.repository.style.StyleRepository
import com.has.android.core.model.Style
import com.has.android.data.database.datasource.style.StyleDataSource
import com.has.android.data.database.model.StyleEntity.Companion.toStyle
import com.has.android.data.database.model.StyleEntity.Companion.toStyleEntity
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