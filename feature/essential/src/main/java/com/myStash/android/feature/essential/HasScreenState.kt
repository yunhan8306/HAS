package com.myStash.android.feature.essential

import com.myStash.android.core.model.Has
import com.myStash.android.core.model.Tag
import com.myStash.android.core.model.Type

data class HasScreenState(
    val hasList: List<Has> = emptyList(),
    val totalTypeList: List<Type> = emptyList(),
    val selectedType: Type = Type(0),
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
    object ResetSelectHas : HasScreenAction

    object ShowSearch : HasScreenAction
    data class ShowItemActivity(val has: Has?): HasScreenAction
}