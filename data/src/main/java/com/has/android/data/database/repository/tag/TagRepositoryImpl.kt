package com.has.android.data.database.repository.tag

import com.has.android.domain.database.database.repository.tag.TagRepository
import com.has.android.core.model.Tag
import com.has.android.data.database.datasource.tag.TagDataSource
import com.has.android.data.database.model.TagEntity.Companion.toEntity
import com.has.android.data.database.model.TagEntity.Companion.toTag
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