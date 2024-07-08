package com.myStash.android.core.data_store.init

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class InitDataStoreDataSourceImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : InitDataStoreDataSource {

    companion object {
        private val PREFERENCE_HAS_INIT = booleanPreferencesKey("has_init")
    }

    override suspend fun setInit(isInit: Boolean) {
        dataStore.edit { preferences ->
            preferences[PREFERENCE_HAS_INIT] = isInit
        }
    }

    override val getInit: Flow<Boolean> = dataStore.data.map { it[PREFERENCE_HAS_INIT] ?: false }
}