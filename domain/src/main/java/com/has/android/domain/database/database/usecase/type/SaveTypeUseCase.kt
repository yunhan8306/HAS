package com.has.android.domain.database.database.usecase.type

import com.has.android.core.model.Type
import com.has.android.domain.database.database.repository.type.TypeRepository
import javax.inject.Inject

class SaveTypeUseCase @Inject constructor(
    private val typeRepository: TypeRepository
) {
    suspend fun invoke(type: Type) =
        typeRepository.insert(type)
}