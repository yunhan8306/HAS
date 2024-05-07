package com.myStash.core.data.usecase.style

import com.myStash.core.data.repository.style.StyleRepository
import com.myStash.core.model.Style
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetStyleListUseCase @Inject constructor(
    private val styleRepository: StyleRepository
) {
    val styleList: Flow<List<Style>> =
        styleRepository.selectAll()
}