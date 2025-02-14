package com.has.android.data.datastore.repository.gender

import com.has.android.domain.database.datasource.repository.gender.GenderRepository
import com.has.android.data.datastore.datasource.gender.GenderDataStoreDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GenderRepositoryImpl @Inject constructor(
    private val genderDataStoreDataSource: GenderDataStoreDataSource
): GenderRepository {
    override suspend fun selectGender(gender: String) =
        genderDataStoreDataSource.selectGender(gender)

    override val selectedGender: Flow<String> = genderDataStoreDataSource.selectedGender
}