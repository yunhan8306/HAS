package com.myStash.core.di

import android.content.Context
import com.myStash.data_base.DataBase
import com.myStash.data_base.item.dao.ItemDao
import com.myStash.data_base.style.dao.StyleDao
import com.myStash.data_base.tag.dao.TagDao
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
    fun providesItemDao(
        dataBase: DataBase
    ) : ItemDao = dataBase.ItemDao()

    @Provides
    fun providesStyleDao(
        dataBase: DataBase
    ) : StyleDao = dataBase.StyleDao()

    @Provides
    fun providesTagDao(
        dataBase: DataBase
    ) : TagDao = dataBase.TagDao()
}
