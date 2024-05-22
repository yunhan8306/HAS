package com.myStash.android.core.data.usecase.style

import com.myStash.android.core.data.repository.style.StyleRepository
import com.myStash.android.core.data.repository.tag.TagRepository
import com.myStash.android.core.model.Style
import com.myStash.android.core.model.Tag
import javax.inject.Inject

class UpdateStyleUseCase @Inject constructor(
    private val styleRepository: StyleRepository
) {
    suspend fun invoke(style: Style) =
        styleRepository.update(style)
}