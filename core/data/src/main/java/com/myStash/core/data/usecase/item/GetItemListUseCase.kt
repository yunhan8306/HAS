package com.myStash.core.data.usecase.item

import com.myStash.core.data.repository.item.ItemRepository
import com.myStash.core.data.repository.tag.TagRepository
import javax.inject.Inject

class GetItemListUseCase @Inject constructor(
    private val itemRepository: ItemRepository
) {
    suspend fun invoke() =
        itemRepository.selectAll()
}