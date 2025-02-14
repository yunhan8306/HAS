package com.has.android.core.di

import com.has.android.core.data.repository.feed.FeedRepository
import com.has.android.core.data.repository.feed.FeedRepositoryImpl
import com.has.android.core.data.repository.gender.GenderRepository
import com.has.android.core.data.repository.gender.GenderRepositoryImpl
import com.has.android.core.data.repository.init.InitRepository
import com.has.android.core.data.repository.init.InitRepositoryImpl
import com.has.android.core.data.repository.has.HasRepository
import com.has.android.core.data.repository.has.HasRepositoryImpl
import com.has.android.core.data.repository.profile.ProfileRepository
import com.has.android.core.data.repository.profile.ProfileRepositoryImpl
import com.has.android.core.data.repository.style.StyleRepository
import com.has.android.core.data.repository.style.StyleRepositoryImpl
import com.has.android.core.data.repository.tag.TagRepository
import com.has.android.core.data.repository.tag.TagRepositoryImpl
import com.has.android.core.data.repository.type.TypeRepository
import com.has.android.core.data.repository.type.TypeRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

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