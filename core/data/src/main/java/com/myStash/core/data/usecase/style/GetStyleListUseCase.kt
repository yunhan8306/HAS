package com.myStash.core.data.usecase.style

import com.myStash.core.data.repository.style.StyleRepository
import com.myStash.core.data.repository.tag.TagRepository
import javax.inject.Inject

class GetStyleListUseCase @Inject constructor(
    private val styleRepository: StyleRepository
) {
    suspend fun invoke() =
        styleRepository.selectAll()
}