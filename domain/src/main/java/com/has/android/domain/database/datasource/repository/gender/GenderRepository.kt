package com.has.android.domain.database.datasource.repository.gender

import kotlinx.coroutines.flow.Flow

interface GenderRepository {
    suspend fun selectGender(gender: String)
    val selectedGender: Flow<String>
}