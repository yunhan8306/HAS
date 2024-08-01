package com.myStash.android.feature.item.feed

import com.myStash.android.core.model.Has
import com.myStash.android.core.model.StyleScreenModel
import com.myStash.android.core.model.Tag
import com.myStash.android.core.model.Type
import java.time.LocalDate

data class AddFeedScreenState(
    val hasList: List<Has> = emptyList(),
    val selectedType: Type? = null,
    val selectedHasList: List<Has> = emptyList(),

    val selectedImageList: List<String> = emptyList(),
    val date: LocalDate = LocalDate.now(),
    val selectedStyle: StyleScreenModel? = null,
    val typeTotalList: List<Type> = emptyList(),
    val tagTotalList: List<Tag> = emptyList(),


    val styleList: List<StyleScreenModel> = emptyList(),
    val tagList: List<Tag> = emptyList(),
    val selectedTagList: List<Tag> = emptyList(),
)

sealed interface AddFeedSideEffect {
    object Finish: AddFeedSideEffect
}

sealed interface AddFeedScreenAction {
    data class SelectStyle(val style: StyleScreenModel?): AddFeedScreenAction
    object DeleteSearchText: AddFeedScreenAction
    data class SelectTag(val tag: Tag): AddFeedScreenAction
}