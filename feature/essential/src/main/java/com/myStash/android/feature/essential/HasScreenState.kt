package com.myStash.android.feature.essential

import com.myStash.android.core.model.Item
import com.myStash.android.core.model.Tag
import com.myStash.android.core.model.Type

data class HasScreenState(
    val itemList: List<Item> = emptyList(),
    val totalTypeList: List<Type> = emptyList(),
    val selectedType: Type = Type(0),
    val totalTagList: List<Tag> = emptyList(),
    val selectedTagList: List<Tag> = emptyList()
)

sealed interface HasSideEffect