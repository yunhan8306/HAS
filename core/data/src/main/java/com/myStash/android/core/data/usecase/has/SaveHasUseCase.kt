package com.myStash.android.core.data.usecase.has

import com.myStash.android.core.data.repository.has.HasRepository
import com.myStash.android.core.model.Has
import javax.inject.Inject

class SaveHasUseCase @Inject constructor(
    private val hasRepository: HasRepository
) {
    suspend fun invoke(has: Has) =
        hasRepository.insert(has)
}