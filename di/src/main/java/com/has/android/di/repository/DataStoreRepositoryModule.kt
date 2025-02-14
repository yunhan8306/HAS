package com.has.android.di.repository

import com.has.android.data.datastore.repository.gender.GenderRepositoryImpl
import com.has.android.data.datastore.repository.init.InitRepositoryImpl
import com.has.android.data.datastore.repository.profile.ProfileRepositoryImpl
import com.has.android.domain.database.datasource.repository.gender.GenderRepository
import com.has.android.domain.database.datasource.repository.init.InitRepository
import com.has.android.domain.database.datasource.repository.profile.ProfileRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DataStoreRepositoryModule {
    @Module
    @InstallIn(SingletonComponent::class)
    interface Binder {
        @Binds
        fun bindsGenderRepository(
            genderRepository: GenderRepositoryImpl
        ): GenderRepository

        @Binds
        fun bindsInitRepository(
            initRepository: InitRepositoryImpl
        ): InitRepository

        @Binds
        fun bindsProfileRepository(
            profileRepository: ProfileRepositoryImpl
        ): ProfileRepository
    }
}