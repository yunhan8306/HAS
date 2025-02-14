package com.has.android.data.datastore.repository.profile

import com.has.android.domain.database.datasource.repository.profile.ProfileRepository
import com.has.android.data.datastore.datasource.profile.ProfileDataStoreDataSource
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