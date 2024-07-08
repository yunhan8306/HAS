package com.myStash.android.core.data.repository.init

import com.myStash.android.core.data_store.init.InitDataStoreDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class InitRepositoryImpl @Inject constructor(
    private val initDataStoreDataSource: InitDataStoreDataSource
): InitRepository {
    override suspend fun setInit(isInit: Boolean) =
        initDataStoreDataSource.setInit(isInit)

    override val getInit: Flow<Boolean> = initDataStoreDataSource.getInit
}