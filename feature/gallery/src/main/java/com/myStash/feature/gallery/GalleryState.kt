package com.myStash.feature.gallery

import com.myStash.core.model.Image

data class GalleryScreenState(
    val imageList: List<Image> = emptyList(),
    val selectedImageList: MutableList<Image> = mutableListOf()
)

sealed interface GallerySideEffect