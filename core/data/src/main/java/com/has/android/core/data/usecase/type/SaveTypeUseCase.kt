package com.has.android.core.data.usecase.type

import com.has.android.core.data.repository.type.TypeRepository
import com.has.android.core.model.Has
import com.has.android.core.model.Type
import javax.inject.Inject

class SaveTypeUseCase @Inject constructor(
    private val typeRepository: TypeRepository
) {
    suspend fun invoke(type: Type) =
        typeRepository.insert(type)
}