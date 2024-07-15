package com.myStash.android.feature.item.has

import com.myStash.android.core.model.Tag
import com.myStash.android.core.model.Type

data class AddHasScreenState(
    val imageUri: String? = null,
    val typeTotalList: List<Type> = emptyList(),
    val selectedType: Type? = null,
    val tagTotalList: List<Tag> = emptyList(),
    val selectedTagList: List<Tag> = emptyList(),
    val searchTagList: List<Tag> = emptyList()
)

sealed interface AddHasSideEffect {
    object Finish: AddHasSideEffect
}