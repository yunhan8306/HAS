package com.has.android.core.data.usecase.tag

import com.has.android.core.data.repository.tag.TagRepository
import com.has.android.core.model.Tag
import javax.inject.Inject

class DeleteTagUseCase @Inject constructor(
    private val tagRepository: TagRepository
) {
    suspend fun invoke(tag: Tag) =
        tagRepository.delete(tag)
}