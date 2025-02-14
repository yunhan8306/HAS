package com.has.android.core.data.usecase.has

import com.has.android.core.data.repository.has.HasRepository
import com.has.android.core.model.Has
import javax.inject.Inject

class SaveHasUseCase @Inject constructor(
    private val hasRepository: HasRepository
) {
    suspend fun invoke(has: Has) =
        hasRepository.insert(has)
}