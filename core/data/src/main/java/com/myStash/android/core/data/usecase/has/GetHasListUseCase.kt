package com.myStash.android.core.data.usecase.has

import com.myStash.android.core.data.repository.has.HasRepository
import com.myStash.android.core.model.Has
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetHasListUseCase @Inject constructor(
    private val hasRepository: HasRepository
) {
    val hasList: Flow<List<Has>> =
        hasRepository.selectAll().map { list -> list.filter { !it.isRemove }.reversed() }
}