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
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DataBaseDataSourceModule {

    @Module
    @InstallIn(SingletonComponent::class)
    interface Binder {
        @Binds
        fun bindsHasDataSource(
            hasDataSource: HasDataSourceImpl
        ): HasDataSource

        @Binds
        fun bindsStyleDataSource(
            styleDataSource: StyleDataSourceImpl
        ): StyleDataSource

        @Binds
        fun bindsTagDataSource(
            tagDataSource: TagDataSourceImpl
        ): TagDataSource

        @Binds
        fun bindsTypeDataSource(
            typeDataSource: TypeDataSourceImpl
        ): TypeDataSource

        @Binds
        fun bindsFeedDataSource(
            feedDataSource: FeedDataSourceImpl
        ): FeedDataSource
    }
}