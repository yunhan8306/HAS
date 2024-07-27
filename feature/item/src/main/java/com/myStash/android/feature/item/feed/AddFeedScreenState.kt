package com.myStash.android.feature.item.feed

import com.myStash.android.core.model.Has
import com.myStash.android.core.model.Image
import com.myStash.android.core.model.Tag
import com.myStash.android.core.model.Type

data class AddFeedScreenState(
    val typeTotalList: List<Type> = emptyList(),
    val hasList: List<Has> = emptyList(),
    val selectedType: Type? = null,
    val selectedHasList: List<Has> = emptyList(),

    val selectedImageList: List<Image> = emptyList(),
)

sealed interface AddFeedSideEffect {
    object Finish: AddFeedSideEffect
}