package com.myStash.android.core.data.usecase.init

import com.myStash.android.core.data.repository.init.InitRepository
import javax.inject.Inject

class GetInitUseCase @Inject constructor(
    private val initRepository: InitRepository
) {
    val init = initRepository.getInit
}