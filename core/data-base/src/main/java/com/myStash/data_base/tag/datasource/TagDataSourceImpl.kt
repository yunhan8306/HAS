package com.myStash.data_base.tag.datasource

import com.myStash.data_base.tag.dao.TagDao
import com.myStash.data_base.tag.entity.TagEntity
import javax.inject.Inject

class TagDataSourceImpl @Inject constructor(
    private val tagDao: TagDao
) : TagDataSource {
    override suspend fun insert(entity: TagEntity): Long =
        tagDao.insert(entity)

    override suspend fun update(entity: TagEntity): Int =
        tagDao.update(entity)

    override suspend fun delete(entity: TagEntity): Int =
        tagDao.delete(entity)

    override suspend fun selectAll(): List<TagEntity> =
        tagDao.selectAll()

}