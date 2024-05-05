package com.myStash.core.data.usecase.style

import com.myStash.core.data.repository.style.StyleRepository
import com.myStash.core.data.repository.tag.TagRepository
import com.myStash.core.model.Style
import com.myStash.core.model.Tag
import javax.inject.Inject

class DeleteStyleUseCase @Inject constructor(
    private val styleRepository: StyleRepository
) {
    suspend fun invoke(style: Style) =
        styleRepository.delete(style)
}