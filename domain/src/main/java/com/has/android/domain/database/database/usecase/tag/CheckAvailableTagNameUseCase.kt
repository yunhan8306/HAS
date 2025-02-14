package com.has.android.domain.database.database.usecase.tag

import com.has.android.domain.database.database.repository.tag.TagRepository
import javax.inject.Inject

class CheckAvailableTagUseCase @Inject constructor(
    private val tagRepository: TagRepository
) {
    suspend fun invoke(name: String) =
        tagRepository.getTag(name)
}