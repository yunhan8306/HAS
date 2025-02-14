package com.has.android.core.di

import android.content.Context
import com.has.android.data_base.DataBase
import com.has.android.data_base.feed.dao.FeedDao
import com.has.android.data_base.has.dao.HasDao
import com.has.android.data_base.style.dao.StyleDao
import com.has.android.data_base.tag.dao.TagDao
import com.has.android.data_base.type.dao.TypeDao
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataBaseModule {
    @Provides
    @Singleton
    fun providesDataBase(
        @ApplicationContext context: Context,
        moshi: Moshi
    ): DataBase = DataBase.build(context, moshi)
}

@Module
@InstallIn(SingletonComponent::class)
class DaoModule {
    @Provides
    fun providesHasDao(
        dataBase: DataBase
    ) : HasDao = dataBase.HasDao()

    @Provides
    fun providesStyleDao(
        dataBase: DataBase
    ) : StyleDao = dataBase.StyleDao()

    @Provides
    fun providesTagDao(
        dataBase: DataBase
    ) : TagDao = dataBase.TagDao()

    @Provides
    fun providesTypeDao(
        dataBase: DataBase
    ) : TypeDao = dataBase.TypeDao()

    @Provides
    fun providesFeedDao(
        dataBase: DataBase
    ): FeedDao = dataBase.FeedDao()
}
