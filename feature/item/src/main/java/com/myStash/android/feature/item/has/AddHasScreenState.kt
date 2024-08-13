package com.myStash.android.feature.item.has

import android.content.Intent
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
    data class Finish(val intent: Intent? = null): AddHasSideEffect
}

sealed interface AddHasScreenAction {
    data class SelectType(val type: Type): AddHasScreenAction
    data class SelectTag(val tag: Tag): AddHasScreenAction
    object ShowSearch: AddHasScreenAction
    object Save: AddHasScreenAction
    object Back: AddHasScreenAction
    object ShowGalleryActivity: AddHasScreenAction
}