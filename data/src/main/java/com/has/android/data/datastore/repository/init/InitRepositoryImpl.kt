package com.has.android.data.datastore.repository.init

import com.has.android.domain.database.datasource.repository.init.InitRepository
import com.has.android.data.datastore.datasource.init.InitDataStoreDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class InitRepositoryImpl @Inject constructor(
    private val initDataStoreDataSource: InitDataStoreDataSource
): InitRepository {
    override suspend fun setInit(isInit: Boolean) =
        initDataStoreDataSource.setInit(isInit)

    override val getInit: Flow<Boolean> = initDataStoreDataSource.getInit
}