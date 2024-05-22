package com.myStash.android.feature.item

import com.myStash.android.core.model.Item
import com.myStash.android.core.model.Tag

data class ItemScreenState(
    val item: Item = Item(),
    val imageUri: String? = null,
    val type: String? = null,
    val brand: Tag? = null,
    val tagList: List<Tag> = emptyList()
) {
    fun initCopy(item: Item?, totalList: List<Tag>) =
        this.copy(
            imageUri = item?.imagePath,
            type = "",
            brand = item?.getBrand(totalList),
            tagList = item?.getTagList(totalList) ?: emptyList(),
        )
}

sealed interface ItemSideEffect {
    object Finish: ItemSideEffect
}