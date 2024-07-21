package com.myStash.android.feature.style

import com.myStash.android.core.model.Style
import com.myStash.android.core.model.StyleScreenModel
import com.myStash.android.core.model.Tag

data class StyleScreenState(
    val styleList: List<StyleScreenModel> = emptyList(),
    val selectedStyle: Style? = null,
    val totalTagList: List<Tag> = emptyList(),
    val selectedTagList: List<Tag> = emptyList()
)

sealed interface StyleSideEffect