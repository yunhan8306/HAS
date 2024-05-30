package com.myStash.android.core.data_store.gender

import kotlinx.coroutines.flow.Flow

interface GenderDataStoreDataSource {
    suspend fun selectGender(gender: String)
    val selectedGender: Flow<String>
}