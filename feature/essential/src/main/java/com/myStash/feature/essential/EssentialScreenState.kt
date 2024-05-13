package com.myStash.feature.essential

import com.myStash.core.model.Image

data class EssentialScreenState(
    val imageList: List<Int> = emptyList(),
    val selectedImageList: List<Int> = emptyList()
)

sealed interface EssentialSideEffect