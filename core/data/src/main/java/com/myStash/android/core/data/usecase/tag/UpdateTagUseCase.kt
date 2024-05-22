package com.myStash.android.core.data.usecase.tag

import com.myStash.android.core.data.repository.tag.TagRepository
import com.myStash.android.core.model.Tag
import javax.inject.Inject

class UpdateTagUseCase @Inject constructor(
    private val tagRepository: TagRepository
) {
    suspend fun invoke(tag: Tag) =
        tagRepository.update(tag)
}