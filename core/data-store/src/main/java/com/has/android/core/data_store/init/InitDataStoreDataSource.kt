package com.has.android.core.data_store.init

import kotlinx.coroutines.flow.Flow

interface InitDataStoreDataSource {
    suspend fun setInit(isInit: Boolean)
    val getInit: Flow<Boolean>
}