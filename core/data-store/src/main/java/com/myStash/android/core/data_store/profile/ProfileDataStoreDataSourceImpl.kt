package com.myStash.android.core.data_store.profile

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ProfileDataStoreDataSourceImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : ProfileDataStoreDataSource {

    companion object {
        private val PREFERENCE_NICK_NAME = stringPreferencesKey("nickName")
        private val PREFERENCE_PROFILE_URI = stringPreferencesKey("profileUri")

    }

    override suspend fun setNickName(nickName: String) {
        dataStore.edit { preferences ->
            preferences[PREFERENCE_NICK_NAME] = nickName
        }
    }

    override val getNickName: Flow<String> = dataStore.data.map { it[PREFERENCE_NICK_NAME] ?: "" }

    override suspend fun setProfileUri(uri: String) {
        dataStore.edit { preferences ->
            preferences[PREFERENCE_PROFILE_URI] = uri
        }
    }

    override val getProfileUri: Flow<String> = dataStore.data.map { it[PREFERENCE_PROFILE_URI] ?: "" }
}