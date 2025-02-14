package com.has.android.data.database.datasource.style

import com.has.android.data.database.dao.style.StyleDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class StyleDataSourceImpl @Inject constructor(
    private val styleDao: StyleDao
) : StyleDataSource {
    override suspend fun insert(entity: com.has.android.data.database.model.StyleEntity): Long =
        styleDao.insert(entity)

    override suspend fun update(entity: com.has.android.data.database.model.StyleEntity): Int =
        styleDao.update(entity)

    override suspend fun delete(entity: com.has.android.data.database.model.StyleEntity): Int =
        styleDao.delete(entity)

    override fun selectAll(): Flow<List<com.has.android.data.database.model.StyleEntity>> =
        styleDao.selectAll()

}