package com.has.android.data.datastore.datasource.profile

import kotlinx.coroutines.flow.Flow

interface ProfileDataStoreDataSource {
    suspend fun setNickName(nickName: String)
    val getNickName: Flow<String>

    suspend fun setProfileUri(uri: String)
    val getProfileUri: Flow<String>
}