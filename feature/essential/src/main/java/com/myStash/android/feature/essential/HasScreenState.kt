package com.myStash.android.feature.essential

import com.myStash.android.core.model.Has
import com.myStash.android.core.model.Tag
import com.myStash.android.core.model.Type

data class HasScreenState(
    val hasList: List<Has> = emptyList(),
    val totalTypeList: List<Type> = emptyList(),
    val selectedType: Type = Type(0),
    val totalTagList: List<Tag> = emptyList(),
    val selectedTagList: List<Tag> = emptyList()
)

sealed interface HasSideEffect