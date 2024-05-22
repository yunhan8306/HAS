package com.myStash.android.core.data.usecase.tag

import com.myStash.android.core.data.repository.tag.TagRepository
import com.myStash.android.core.model.Tag
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTagListUseCase @Inject constructor(
    private val tagRepository: TagRepository
) {
    val tagList: Flow<List<Tag>> =
        tagRepository.selectAll()
}