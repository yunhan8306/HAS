package com.myStash.android.core.data.usecase.style

import com.myStash.android.core.data.repository.style.StyleRepository
import com.myStash.android.core.model.Style
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetStyleListUseCase @Inject constructor(
    private val styleRepository: StyleRepository
) {
    val styleList: Flow<List<Style>> =
        styleRepository.selectAll()
}