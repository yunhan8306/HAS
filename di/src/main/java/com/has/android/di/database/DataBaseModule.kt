package com.has.android.di.database

import android.content.Context
import com.has.android.data.database.dao.DataBase
import com.has.android.data.database.dao.feed.FeedDao
import com.has.android.data.database.dao.has.HasDao
import com.has.android.data.database.dao.style.StyleDao
import com.has.android.data.database.dao.tag.TagDao
import com.has.android.data.database.dao.type.TypeDao
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
