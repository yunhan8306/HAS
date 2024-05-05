package com.myStash.data_base.style.datasource

import com.myStash.data_base.style.dao.StyleDao
import com.myStash.data_base.style.entity.StyleEntity
import javax.inject.Inject

class StyleDataSourceImpl @Inject constructor(
    private val styleDao: StyleDao
) : StyleDataSource {
    override suspend fun insert(entity: StyleEntity): Long =
        styleDao.insert(entity)

    override suspend fun update(entity: StyleEntity): Int =
        styleDao.update(entity)

    override suspend fun delete(entity: StyleEntity): Int =
        styleDao.delete(entity)

    override suspend fun selectAll(): List<StyleEntity> =
        styleDao.selectAll()

}