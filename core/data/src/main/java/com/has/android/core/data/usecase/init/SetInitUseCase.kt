package com.has.android.core.data.usecase.init

import com.has.android.core.data.repository.init.InitRepository
import javax.inject.Inject

class SetInitUseCase @Inject constructor(
    private val initRepository: InitRepository
) {
    suspend operator fun invoke(isInit: Boolean) {
        initRepository.setInit(isInit)
    }
}