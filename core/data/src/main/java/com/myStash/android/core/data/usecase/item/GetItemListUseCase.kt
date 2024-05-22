package com.myStash.android.core.data.usecase.item

import com.myStash.android.core.data.repository.item.ItemRepository
import com.myStash.android.core.model.Item
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetItemListUseCase @Inject constructor(
    private val itemRepository: ItemRepository
) {
    val itemList: Flow<List<Item>> =
        itemRepository.selectAll()
}