package com.myStash.android.core.di

import com.myStash.android.data_base.item.datasource.ItemDataSource
import com.myStash.android.data_base.item.datasource.ItemDataSourceImpl
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
interface DataSourceModule {

    @Binds
    fun bindsItemDataSource(
        itemDataSource: ItemDataSourceImpl
    ): ItemDataSource

    @Binds
    fun bindsStyleDataSource(
        styleDataSource: StyleDataSourceImpl
    ): StyleDataSource

    @Binds
    fun bindsTagDataSource(
        tagDataSource: TagDataSourceImpl
    ): TagDataSource
}