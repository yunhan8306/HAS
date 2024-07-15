package com.myStash.android.core.di

import com.myStash.android.data_base.has.datasource.HasDataSource
import com.myStash.android.data_base.has.datasource.HasDataSourceImpl
import com.myStash.android.data_base.style.datasource.StyleDataSource
import com.myStash.android.data_base.style.datasource.StyleDataSourceImpl
import com.myStash.android.data_base.tag.datasource.TagDataSource
import com.myStash.android.data_base.tag.datasource.TagDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataBaseDataSourceModule {

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
}