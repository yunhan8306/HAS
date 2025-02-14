package com.has.android.domain.database.database.usecase.has

import com.has.android.core.model.Has
import com.has.android.domain.database.database.repository.has.HasRepository
import javax.inject.Inject

class UpdateHasUseCase @Inject constructor(
    private val hasRepository: HasRepository
) {
    suspend fun invoke(has: Has) =
        hasRepository.update(has)
}