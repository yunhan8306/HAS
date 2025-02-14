package com.has.android.core.data.usecase.has

import com.has.android.core.data.repository.has.HasRepository
import com.has.android.core.model.Has
import javax.inject.Inject

class UpdateHasUseCase @Inject constructor(
    private val hasRepository: HasRepository
) {
    suspend fun invoke(has: Has) =
        hasRepository.update(has)
}