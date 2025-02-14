package com.has.android.domain.database.datasource.usecase.init


import com.has.android.domain.database.datasource.repository.init.InitRepository
import javax.inject.Inject

class GetInitUseCase @Inject constructor(
    private val initRepository: InitRepository
) {
    val init = initRepository.getInit
}