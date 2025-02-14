package com.has.android.domain.database.datasource.repository.init

import kotlinx.coroutines.flow.Flow

interface InitRepository {
    suspend fun setInit(isInit: Boolean)
    val getInit: Flow<Boolean>
}