package com.myStash.android.core.data.usecase.item

import com.myStash.android.core.data.repository.item.ItemRepository
import com.myStash.android.core.data.repository.tag.TagRepository
import com.myStash.android.core.model.Item
import com.myStash.android.core.model.Tag
import javax.inject.Inject

class SaveItemUseCase @Inject constructor(
    private val itemRepository: ItemRepository
) {
    suspend fun invoke(item: Item) =
        itemRepository.insert(item)
}