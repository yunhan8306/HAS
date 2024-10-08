package com.myStash.android.core.data.usecase.type

import com.myStash.android.core.data.repository.type.TypeRepository
import javax.inject.Inject

class CheckAvailableTypeUseCase @Inject constructor(
    private val typeRepository: TypeRepository
) {
    suspend fun invoke(name: String) =
        typeRepository.getType(name)
}