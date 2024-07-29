package com.myStash.android.feature.item.style

import com.myStash.android.core.model.Has
import com.myStash.android.core.model.Type
import com.myStash.android.core.model.getTotalType

data class AddStyleScreenState(
    val typeTotalList: List<Type> = emptyList(),
    val hasList: List<Has> = emptyList(),
    val selectedType: Type = getTotalType(),
    val selectedHasList: List<Has> = emptyList()
)

sealed interface AddStyleSideEffect {
    object Finish: AddStyleSideEffect
}