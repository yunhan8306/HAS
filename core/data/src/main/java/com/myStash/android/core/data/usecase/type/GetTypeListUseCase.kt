package com.myStash.android.core.data.usecase.type

import com.myStash.android.core.data.repository.type.TypeRepository
import com.myStash.android.core.model.Type
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetTypeListUseCase @Inject constructor(
    private val typeRepository: TypeRepository
) {
    val typeList: Flow<List<Type>> =
        typeRepository.selectAll().map { it.filter { !it.isRemove } }

    val typeRemoveList : Flow<List<Type>> =
        typeRepository.selectAll().map { it.filter { it.isRemove } }
}