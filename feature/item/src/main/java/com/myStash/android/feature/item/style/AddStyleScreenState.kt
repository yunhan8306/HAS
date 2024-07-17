package com.myStash.android.feature.item.style

import com.myStash.android.core.model.Has
import com.myStash.android.core.model.Tag
import com.myStash.android.core.model.Type

data class AddStyleScreenState(
    val typeTotalList: List<Type> = emptyList(),
    val hasList: List<Has> = emptyList(),
    val selectedType: Type? = null,
    val selectedHasList: List<Has> = emptyList()
)

sealed interface AddStyleSideEffect {
    object Finish: AddStyleSideEffect
}