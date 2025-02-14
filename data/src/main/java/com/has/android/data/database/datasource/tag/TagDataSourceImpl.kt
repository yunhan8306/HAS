package com.has.android.data.database.datasource.tag

import com.has.android.data.database.dao.tag.TagDao
import com.has.android.data.database.model.TagEntity
import kotlinx.coroutines.flow.Flow
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

    override fun selectAll(): Flow<List<TagEntity>> =
        tagDao.selectAll()

    override suspend fun getTag(name: String): TagEntity? =
        tagDao.getTag(name)

}