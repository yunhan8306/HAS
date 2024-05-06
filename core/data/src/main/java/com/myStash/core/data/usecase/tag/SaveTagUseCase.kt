package com.myStash.core.data.usecase.tag

import com.myStash.core.data.repository.tag.TagRepository
import com.myStash.core.model.Tag
import javax.inject.Inject

class SaveTagUseCase @Inject constructor(
    private val tagRepository: TagRepository
) {
    suspend fun invoke(tag: Tag) =
        tagRepository.insert(tag)
}