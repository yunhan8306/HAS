package com.has.android.feature.item.feed

import android.content.Intent
import com.has.android.core.model.Has
import com.has.android.core.model.StyleScreenModel
import com.has.android.core.model.Tag
import com.has.android.core.model.Type
import java.time.LocalDate

data class AddFeedScreenState(
    val isEdit: Boolean = false,
    val hasList: List<Has> = emptyList(),
    val selectedType: Type? = null,
    val selectedHasList: List<Has> = emptyList(),

    val selectedImageList: List<String> = emptyList(),
    val calenderDate: LocalDate = LocalDate.now(),
    val selectedStyle: StyleScreenModel? = null,
    val typeTotalList: List<Type> = emptyList(),
    val tagTotalList: List<Tag> = emptyList(),


    val styleList: List<StyleScreenModel> = emptyList(),
    val tagList: List<Tag> = emptyList(),
    val selectedTagList: List<Tag> = emptyList(),
)

sealed interface AddFeedSideEffect {
    data class Finish(val intent: Intent?): AddFeedSideEffect
}

sealed interface AddFeedScreenAction {
    data class SelectStyle(val style: StyleScreenModel?): AddFeedScreenAction
    data class SelectTag(val tag: Tag): AddFeedScreenAction
    data class SelectDate(val date: LocalDate): AddFeedScreenAction
    data class UnselectImage(val uri: String): AddFeedScreenAction
    object PrevMonth: AddFeedScreenAction
    object NextMonth: AddFeedScreenAction
    object DeleteSearchText: AddFeedScreenAction
    object SaveFeed: AddFeedScreenAction
    object ShowGalleryActivity: AddFeedScreenAction
    object ShowStyleSheet: AddFeedScreenAction
}