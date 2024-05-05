package com.myStash.core.data.usecase.item

import com.myStash.core.data.repository.item.ItemRepository
import com.myStash.core.data.repository.tag.TagRepository
import com.myStash.core.model.Item
import com.myStash.core.model.Tag
import javax.inject.Inject

class DeleteItemUseCase @Inject constructor(
    private val itemRepository: ItemRepository
) {
    suspend fun invoke(item: Item) =
        itemRepository.delete(item)
}