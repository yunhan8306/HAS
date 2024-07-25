package com.myStash.android.feature.gallery

import com.myStash.android.core.model.Image

data class GalleryScreenState(
    val focusImage: Image? = null,
    val imageList: List<Image> = emptyList(),
    val selectedImageList: List<Image> = emptyList()
)

sealed interface GallerySideEffect