package com.myStash.core.data.usecase.tag

import com.myStash.core.data.repository.tag.TagRepository
import javax.inject.Inject

class GetTagListUseCase @Inject constructor(
    private val tagRepository: TagRepository
) {
    suspend fun invoke() =
        tagRepository.selectAll()
}