package com.myStash.android.core.data.repository.profile

import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    suspend fun setNickName(nickName: String)
    val getNickName: Flow<String>

    suspend fun setProfileUri(uri: String)
    val getProfileUri: Flow<String>
}