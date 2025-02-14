package com.has.android.feature.search.ui

import android.content.Intent
import com.has.android.core.model.Tag

data class SearchScreenState(
    val totalTagList: List<Tag> = emptyList(),
    val selectedTagList: List<Tag> = emptyList(),
    val searchTagList: List<Tag> = emptyList(),
    val selectedCntText: String? = null,
)

sealed interface SearchSideEffect {
    data class Complete(val intent: Intent): SearchSideEffect
}

sealed interface SearchAction {
    data class SelectTag(val tag: Tag): SearchAction
    object Confirm: SearchAction
    object DeleteText: SearchAction
    object Finish: SearchAction
}