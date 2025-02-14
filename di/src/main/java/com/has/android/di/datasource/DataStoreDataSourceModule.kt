package com.has.android.di.datasource

import com.has.android.data.database.datasource.feed.FeedDataSource
import com.has.android.data.database.datasource.feed.FeedDataSourceImpl
import com.has.android.data.database.datasource.has.HasDataSource
import com.has.android.data.database.datasource.has.HasDataSourceImpl
import com.has.android.data.database.datasource.style.StyleDataSource
import com.has.android.data.database.datasource.style.StyleDataSourceImpl
import com.has.android.data.database.datasource.tag.TagDataSource
import com.has.android.data.database.datasource.tag.TagDataSourceImpl
import com.has.android.data.database.datasource.type.TypeDataSource
import com.has.android.data.database.datasource.type.TypeDataSourceImpl
import com.has.android.data.datastore.datasource.gender.GenderDataStoreDataSource
import com.has.android.data.datastore.datasource.gender.GenderDataStoreDataSourceImpl
import com.has.android.data.datastore.datasource.init.InitDataStoreDataSource
import com.has.android.data.datastore.datasource.init.InitDataStoreDataSourceImpl
import com.has.android.data.datastore.datasource.profile.ProfileDataStoreDataSource
import com.has.android.data.datastore.datasource.profile.ProfileDataStoreDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DataStoreDataSourceModule {

    @Module
    @InstallIn(SingletonComponent::class)
    interface Binder {
        @Binds
        fun bindsGenderDataSource(
            genderDataStoreDataSource: GenderDataStoreDataSourceImpl
        ): GenderDataStoreDataSource

        @Binds
        fun bindsInitDataSource(
            initDataStoreDataSource: InitDataStoreDataSourceImpl
        ): InitDataStoreDataSource

        @Binds
        fun bindsProfileDataSource(
            profileDataSource: ProfileDataStoreDataSourceImpl
        ): ProfileDataStoreDataSource
    }
}