package com.myStash.android.core.data_store.gender

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GenderDataStoreDataSourceImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : GenderDataStoreDataSource {

    companion object {
        private val PREFERENCE_SELECTED_GENDER = stringPreferencesKey("selected_gender")
    }
    override suspend fun selectGender(gender: String) {
        dataStore.edit { preferences ->
            preferences[PREFERENCE_SELECTED_GENDER] = gender
        }
    }
    override val selectedGender: Flow<String> = dataStore.data.map { it[PREFERENCE_SELECTED_GENDER] ?: "" }
}