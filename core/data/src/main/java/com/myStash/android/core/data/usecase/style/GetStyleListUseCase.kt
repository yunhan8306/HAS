package com.myStash.android.core.data.usecase.style

import com.myStash.android.core.data.repository.style.StyleRepository
import com.myStash.android.core.data.usecase.has.GetHasListUseCase
import com.myStash.android.core.model.Style.Companion.toStyleScreenModel
import com.myStash.android.core.model.StyleScreenModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetStyleListUseCase @Inject constructor(
    private val styleRepository: StyleRepository,
    private val getHasListUseCase: GetHasListUseCase
) {

    val styleList: Flow<List<StyleScreenModel>> =
        styleRepository.selectAll().map { styleList ->
            val hasList = getHasListUseCase.hasList.first()
            styleList.reversed().map { style -> style.toStyleScreenModel(hasList) }
        }
}