package com.myStash.core.data.repository.tag

import com.myStash.core.model.Tag
import com.myStash.data_base.tag.datasource.TagDataSource
import com.myStash.data_base.tag.entity.TagEntity
import com.myStash.data_base.tag.entity.TagEntity.Companion.toEntity
import com.myStash.data_base.tag.entity.TagEntity.Companion.toTag
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TagRepositoryImpl @Inject constructor(
    private val tagDataSource: TagDataSource
) : TagRepository {
    override suspend fun insert(tag: Tag): Long =
        tagDataSource.insert(tag.toEntity())

    override suspend fun update(tag: Tag): Int =
        tagDataSource.update(tag.toEntity())

    override suspend fun delete(tag: Tag): Int =
        tagDataSource.delete(tag.toEntity())

    override fun selectAll(): Flow<List<Tag>> =
        tagDataSource.selectAll().map { list -> list.map { it.toTag() } }

    override suspend fun getTag(name: String): Tag? =
        tagDataSource.getTag(name)?.toTag()
}