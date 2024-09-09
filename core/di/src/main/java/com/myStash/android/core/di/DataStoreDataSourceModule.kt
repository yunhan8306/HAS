package com.myStash.android.core.di

import com.myStash.android.core.data_store.gender.GenderDataStoreDataSource
import com.myStash.android.core.data_store.gender.GenderDataStoreDataSourceImpl
import com.myStash.android.core.data_store.init.InitDataStoreDataSource
import com.myStash.android.core.data_store.init.InitDataStoreDataSourceImpl
import com.myStash.android.core.data_store.profile.ProfileDataStoreDataSource
import com.myStash.android.core.data_store.profile.ProfileDataStoreDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataStoreDataSourceModule {
    @Binds
    fun bindsGenderDataStoreDataSource(genderDataStoreDataSource: GenderDataStoreDataSourceImpl): GenderDataStoreDataSource
    @Binds
    fun bindsInitDataStoreDataSource(initDataStoreDataSource: InitDataStoreDataSourceImpl): InitDataStoreDataSource
    @Binds
    fun bindsProfileDataStoreDataSource(profileDataStoreDataSource: ProfileDataStoreDataSourceImpl): ProfileDataStoreDataSource
}