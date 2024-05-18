package com.myStash.feature.gallery

import com.myStash.core.model.Image

data class GalleryScreenState(
    val imageList: List<Image> = emptyList(),
    val selectedImageList: List<Image> = emptyList()
)

sealed interface GallerySideEffect