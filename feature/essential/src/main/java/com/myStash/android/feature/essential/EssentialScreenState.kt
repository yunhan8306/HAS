package com.myStash.android.feature.essential

import com.myStash.android.core.model.Item
import com.myStash.android.core.model.Tag
import com.myStash.android.core.model.Type

data class EssentialScreenState(
    val itemList: List<Item> = emptyList(),
    val typeTotalList: List<Type> = emptyList(),
    val selectedType: Type = Type(0),
    val tagTotalList: List<Tag> = emptyList(),
    val selectTagList: List<Tag> = emptyList()
)

sealed interface EssentialSideEffect