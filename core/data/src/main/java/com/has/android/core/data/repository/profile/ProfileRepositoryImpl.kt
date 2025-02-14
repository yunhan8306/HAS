package com.has.android.core.data.repository.profile

import com.has.android.core.data_store.init.InitDataStoreDataSource
import com.has.android.core.data_store.profile.ProfileDataStoreDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val profileDataStoreDataSource: ProfileDataStoreDataSource,
): ProfileRepository {
    override suspend fun setNickName(nickName: String) = profileDataStoreDataSource.setNickName(nickName)

    override val getNickName: Flow<String> = profileDataStoreDataSource.getNickName

    override suspend fun setProfileUri(uri: String) = profileDataStoreDataSource.setProfileUri(uri)

    override val getProfileUri: Flow<String> = profileDataStoreDataSource.getProfileUri
}