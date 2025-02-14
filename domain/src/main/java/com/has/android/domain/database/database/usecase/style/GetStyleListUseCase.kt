package com.has.android.domain.database.database.usecase.style

import com.has.android.core.model.Style.Companion.toStyleScreenModel
import com.has.android.core.model.StyleScreenModel
import com.has.android.domain.database.database.repository.style.StyleRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetStyleListUseCase @Inject constructor(
    private val styleRepository: StyleRepository,
    private val getHasListUseCase: com.has.android.domain.database.database.usecase.has.GetHasListUseCase
) {

    val styleList: Flow<List<StyleScreenModel>> =
        styleRepository.selectAll().map { styleList ->
            val hasList = getHasListUseCase.hasList.first()
            styleList.filter { !it.isRemove }.reversed().map { style -> style.toStyleScreenModel(hasList) }
        }
}