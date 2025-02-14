package com.has.android.domain.database.database.usecase.style

import com.has.android.core.model.Style
import com.has.android.domain.database.database.repository.style.StyleRepository
import javax.inject.Inject

class DeleteStyleUseCase @Inject constructor(
    private val styleRepository: StyleRepository
) {
    suspend fun invoke(style: Style) =
        styleRepository.update(style.copy(isRemove = true))
}