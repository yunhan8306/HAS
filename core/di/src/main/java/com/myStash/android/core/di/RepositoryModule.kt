package com.myStash.android.core.di

import com.myStash.android.core.data.repository.gender.GenderRepository
import com.myStash.android.core.data.repository.gender.GenderRepositoryImpl
import com.myStash.android.core.data.repository.init.InitRepository
import com.myStash.android.core.data.repository.init.InitRepositoryImpl
import com.myStash.android.core.data.repository.has.HasRepository
import com.myStash.android.core.data.repository.has.HasRepositoryImpl
import com.myStash.android.core.data.repository.style.StyleRepository
import com.myStash.android.core.data.repository.style.StyleRepositoryImpl
import com.myStash.android.core.data.repository.tag.TagRepository
import com.myStash.android.core.data.repository.tag.TagRepositoryImpl
import com.myStash.android.core.data.repository.type.TypeRepository
import com.myStash.android.core.data.repository.type.TypeRepositoryImpl
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
    fun bindsGenderRepository(
        genderRepository: GenderRepositoryImpl
    ): GenderRepository

    @Binds
    fun bindsInitRepository(
        initRepository: InitRepositoryImpl
    ): InitRepository
}