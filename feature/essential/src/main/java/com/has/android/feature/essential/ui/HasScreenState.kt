package com.has.android.feature.essential.ui

import com.has.android.core.model.Has
import com.has.android.core.model.Tag
import com.has.android.core.model.Type
import com.has.android.core.model.getTotalType

data class HasScreenState(
    val hasList: List<Has> = emptyList(),
    val totalTypeList: List<Type> = emptyList(),
    val selectedType: Type = getTotalType(),
    val isFoldTag: Boolean = false,
    val totalTagList: List<Tag> = emptyList(),
    val selectedTagList: List<Tag> = emptyList(),
    val searchTagList: List<Tag> = emptyList(),
    val selectedHasList: List<Has> = emptyList()
)

sealed interface HasSideEffect

sealed interface HasScreenAction {
    data class SelectHas(val has: Has) : HasScreenAction
    data class SelectType(val type: Type) : HasScreenAction
    data class SelectTag(val tag: Tag) : HasScreenAction
    data class SetTagList(val tagIdList: List<Long>) : HasScreenAction
    data class ShowItemActivity(val has: Has?): HasScreenAction
    data class DeleteHas(val has: Has): HasScreenAction
    object ResetSelectHas : HasScreenAction
    object ShowSearch : HasScreenAction
    object TagToggle: HasScreenAction
}