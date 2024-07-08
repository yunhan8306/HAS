package com.myStash.android.core.di

import com.myStash.android.core.data.repository.gender.GenderRepository
import com.myStash.android.core.data.repository.gender.GenderRepositoryImpl
import com.myStash.android.core.data.repository.init.InitRepository
import com.myStash.android.core.data.repository.init.InitRepositoryImpl
import com.myStash.android.core.data.repository.item.ItemRepository
import com.myStash.android.core.data.repository.item.ItemRepositoryImpl
import com.myStash.android.core.data.repository.style.StyleRepository
import com.myStash.android.core.data.repository.style.StyleRepositoryImpl
import com.myStash.android.core.data.repository.tag.TagRepository
import com.myStash.android.core.data.repository.tag.TagRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun bindsItemRepository(
        itemRepository: ItemRepositoryImpl
    ): ItemRepository

    @Binds
    fun bindsStyleRepository(
        styleRepository: StyleRepositoryImpl
    ): StyleRepository

    @Binds
    fun bindsTagRepository(
        tagRepository: TagRepositoryImpl
    ): TagRepository

    @Binds
    fun bindsGenderRepository(
        genderRepository: GenderRepositoryImpl
    ): GenderRepository

    @Binds
    fun bindsInitRepository(
        initRepository: InitRepositoryImpl
    ): InitRepository
}