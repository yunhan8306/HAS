package com.has.android.core.data.usecase.style

import com.has.android.core.data.repository.style.StyleRepository
import com.has.android.core.model.Style
import javax.inject.Inject

class SaveStyleUseCase @Inject constructor(
    private val styleRepository: StyleRepository
) {
    suspend fun invoke(style: Style) =
        styleRepository.insert(style)
}