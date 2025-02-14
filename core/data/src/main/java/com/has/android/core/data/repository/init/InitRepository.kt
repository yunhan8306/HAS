package com.has.android.core.data.repository.init

import kotlinx.coroutines.flow.Flow

interface InitRepository {
    suspend fun setInit(isInit: Boolean)
    val getInit: Flow<Boolean>
}