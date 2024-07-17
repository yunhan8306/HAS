package com.myStash.android.core.data.usecase.type

import com.myStash.android.core.data.repository.has.HasRepository
import com.myStash.android.core.data.repository.type.TypeRepository
import com.myStash.android.core.model.Has
import com.myStash.android.core.model.Type
import javax.inject.Inject

class UpdateTypeUseCase @Inject constructor(
    private val typeRepository: TypeRepository
) {
    suspend fun invoke(type: Type) =
        typeRepository.update(type)
}