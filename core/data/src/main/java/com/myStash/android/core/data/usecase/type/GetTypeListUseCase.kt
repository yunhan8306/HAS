package com.myStash.android.core.data.usecase.type

import com.myStash.android.core.data.repository.type.TypeRepository
import com.myStash.android.core.model.Has
import com.myStash.android.core.model.Type
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTypeListUseCase @Inject constructor(
    private val typeRepository: TypeRepository
) {
    val typeList: Flow<List<Type>> =
        typeRepository.selectAll()
}