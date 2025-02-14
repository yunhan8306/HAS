package com.has.android.core.data.repository.has

import com.has.android.core.model.Has
import com.has.android.data_base.has.datasource.HasDataSource
import com.has.android.data_base.has.entity.HasEntity.Companion.toHas
import com.has.android.data_base.has.entity.HasEntity.Companion.toHasEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class HasRepositoryImpl @Inject constructor(
    private val hasDataSource: HasDataSource
) : HasRepository {
    override suspend fun insert(has: Has): Long =
        hasDataSource.insert(has.toHasEntity())
    override suspend fun update(has: Has): Int =
        hasDataSource.update(has.toHasEntity())
    override suspend fun delete(has: Has): Int =
        hasDataSource.delete(has.toHasEntity())
    override fun selectAll() : Flow<List<Has>> =
        hasDataSource.selectAll().map { list -> list.map { it.toHas() } }
}