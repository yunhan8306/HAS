package com.has.android.core.data.usecase.type

import com.has.android.core.data.repository.type.TypeRepository
import javax.inject.Inject

class CheckAvailableTypeUseCase @Inject constructor(
    private val typeRepository: TypeRepository
) {
    suspend fun invoke(name: String) =
        typeRepository.getType(name)
}