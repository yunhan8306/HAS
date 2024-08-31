package com.myStash.android.feature.item.style

import android.content.Intent
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
    data class Finish(val intent: Intent?): AddStyleSideEffect
}

sealed interface AddStyleScreenAction {
    data class SelectType(val type: Type): AddStyleScreenAction
    data class SelectHas(val has: Has): AddStyleScreenAction
    object SaveStyle: AddStyleScreenAction

}