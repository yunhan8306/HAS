package com.has.android.domain.database.database.usecase.has

import com.has.android.core.model.Has
import com.has.android.domain.database.database.repository.has.HasRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetHasListUseCase @Inject constructor(
    private val hasRepository: HasRepository
) {
    val hasList: Flow<List<Has>> =
        hasRepository.selectAll().map { list -> list.filter { !it.isRemove }.reversed() }
}