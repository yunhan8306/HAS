package com.has.android.domain.database.database.usecase.tag

import com.has.android.core.model.Tag
import com.has.android.domain.database.database.repository.tag.TagRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetTagListUseCase @Inject constructor(
    private val tagRepository: TagRepository
) {
    val tagList: Flow<List<Tag>> =
        tagRepository.selectAll().map { it.filter { !it.isRemove } }
}