package com.myStash.android.feature.item.style

import com.myStash.android.core.model.Item
import com.myStash.android.core.model.Tag
import com.myStash.android.core.model.Type

data class AddStyleScreenState(
    val hasList: List<Item> = emptyList(),
    val typeTotalList: List<Type> = emptyList(),
    val selectedType: Type? = null,
    val tagTotalList: List<Tag> = emptyList(),
    val selectedTagList: List<Tag> = emptyList(),
    val searchTagList: List<Tag> = emptyList()
)

sealed interface AddStyleSideEffect {
    object Finish: AddStyleSideEffect
}