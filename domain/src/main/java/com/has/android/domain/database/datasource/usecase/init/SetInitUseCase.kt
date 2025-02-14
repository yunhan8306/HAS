package com.has.android.domain.database.datasource.usecase.init

import com.has.android.domain.database.datasource.repository.init.InitRepository
import javax.inject.Inject

class SetInitUseCase @Inject constructor(
    private val initRepository: InitRepository
) {
    suspend operator fun invoke(isInit: Boolean) {
        initRepository.setInit(isInit)
    }
}