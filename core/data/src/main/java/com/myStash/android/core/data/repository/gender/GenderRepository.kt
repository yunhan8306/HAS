package com.myStash.android.core.data.repository.gender

import kotlinx.coroutines.flow.Flow

interface GenderRepository {
    suspend fun selectGender(gender: String)
    val selectedGender: Flow<String>
}