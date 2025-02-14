package com.has.android.di.repository

import com.has.android.domain.database.database.repository.feed.FeedRepository
import com.has.android.data.database.repository.feed.FeedRepositoryImpl
import com.has.android.domain.database.database.repository.has.HasRepository
import com.has.android.data.database.repository.has.HasRepositoryImpl
import com.has.android.domain.database.database.repository.style.StyleRepository
import com.has.android.domain.database.database.repository.tag.TagRepository
import com.has.android.domain.database.database.repository.type.TypeRepository
import com.has.android.data.database.repository.style.StyleRepositoryImpl
import com.has.android.data.database.repository.tag.TagRepositoryImpl
import com.has.android.data.database.repository.type.TypeRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DataBaseRepositoryModule {

    @Module
    @InstallIn(SingletonComponent::class)
    interface Binder {
        @Binds
        fun bindsHasRepository(
            hasRepository: HasRepositoryImpl
        ): HasRepository

        @Binds
        fun bindsStyleRepository(
            styleRepository: StyleRepositoryImpl
        ): StyleRepository

        @Binds
        fun bindsTagRepository(
            tagRepository: TagRepositoryImpl
        ): TagRepository

        @Binds
        fun bindsTypeRepository(
            typeRepository: TypeRepositoryImpl
        ): TypeRepository

        @Binds
        fun bindsFeedRepository(
            feedRepository: FeedRepositoryImpl
        ): FeedRepository
    }
}