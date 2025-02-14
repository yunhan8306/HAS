package com.has.android.data.datastore.datasource.init

import kotlinx.coroutines.flow.Flow

interface InitDataStoreDataSource {
    suspend fun setInit(isInit: Boolean)
    val getInit: Flow<Boolean>
}