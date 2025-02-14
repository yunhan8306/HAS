package com.has.android.domain.database.database.usecase.tag

import com.has.android.core.model.Tag
import com.has.android.domain.database.database.repository.tag.TagRepository
import javax.inject.Inject

class SaveTagUseCase @Inject constructor(
    private val tagRepository: TagRepository
) {
    suspend fun invoke(tag: Tag) =
        tagRepository.insert(tag)
}