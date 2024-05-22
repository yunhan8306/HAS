package com.myStash.android.core.data.usecase.tag

import com.myStash.android.core.data.repository.tag.TagRepository
import javax.inject.Inject

class CheckAvailableTagUseCase @Inject constructor(
    private val tagRepository: TagRepository
) {
    suspend fun invoke(name: String) =
        tagRepository.getTag(name)
}