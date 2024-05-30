package com.myStash.android.core.data.repository.gender

import com.myStash.android.core.data_store.gender.GenderDataStoreDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GenderRepositoryImpl @Inject constructor(
    private val genderDataStoreDataSource: GenderDataStoreDataSource
): GenderRepository {
    override suspend fun selectGender(gender: String) =
        genderDataStoreDataSource.selectGender(gender)

    override val selectedGender: Flow<String> = genderDataStoreDataSource.selectedGender
}