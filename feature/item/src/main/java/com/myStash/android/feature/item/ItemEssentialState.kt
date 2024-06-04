package com.myStash.android.feature.item

import com.myStash.android.core.model.Tag
import com.myStash.android.core.model.Type

data class EssentialItemScreenState(
    val imageUri: String? = null,
    val typeTotalList: List<Type> = emptyList(),
    val selectedType: Type? = null,
    val tagTotalList: List<Tag> = emptyList(),
    val selectedTagList: List<Tag> = emptyList(),
    val searchTagList: List<Tag> = emptyList()
)

sealed interface EssentialItemSideEffect {
    object Finish: EssentialItemSideEffect
}