package com.has.android.data.datastore.datasource.gender

import kotlinx.coroutines.flow.Flow

interface GenderDataStoreDataSource {
    suspend fun selectGender(gender: String)
    val selectedGender: Flow<String>
}