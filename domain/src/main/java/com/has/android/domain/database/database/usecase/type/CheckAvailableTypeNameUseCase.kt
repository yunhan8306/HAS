package com.has.android.domain.database.database.usecase.type

import com.has.android.domain.database.database.repository.type.TypeRepository
import javax.inject.Inject

class CheckAvailableTypeUseCase @Inject constructor(
    private val typeRepository: TypeRepository
) {
    suspend fun invoke(name: String) =
        typeRepository.getType(name)
}