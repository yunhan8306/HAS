package com.myStash.android.feature.essential

import com.myStash.android.core.model.Item
import com.myStash.android.core.model.Tag

data class EssentialScreenState(
    val itemList: List<Item> = emptyList(),
    val tagTotalList: List<Tag> = emptyList(),
    val selectTagList: List<Tag> = emptyList()
)

sealed interface EssentialSideEffect