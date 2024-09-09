package com.myStash.android.feature.style

import com.myStash.android.core.model.StyleScreenModel
import com.myStash.android.core.model.Tag
import com.myStash.android.core.model.Type

data class StyleScreenState(
    val styleList: List<StyleScreenModel> = emptyList(),
    val selectedStyle: StyleScreenModel? = null,
    val isFoldTag: Boolean = false,
    val totalTypeList: List<Type> = emptyList(),
    val totalTagList: List<Tag> = emptyList(),
    val searchTagList: List<Tag> = emptyList(),
    val selectedTagList: List<Tag> = emptyList()
)

sealed interface StyleSideEffect

sealed interface StyleScreenAction {
    data class SelectStyle(val style: StyleScreenModel?): StyleScreenAction
    data class SelectTag(val tag: Tag): StyleScreenAction
    data class ShowMoreStyle(val style: StyleScreenModel): StyleScreenAction
    data class SetTagList(val tagIdList: List<Long>): StyleScreenAction
    data class ShowItemActivity(val style: StyleScreenModel): StyleScreenAction
    data class DeleteStyle(val style: StyleScreenModel?): StyleScreenAction
    object ShowSearch: StyleScreenAction
    object ResetSelectStyle: StyleScreenAction
    object TagToggle: StyleScreenAction
}
