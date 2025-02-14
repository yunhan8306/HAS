package com.has.android.feature.gallery.ui

import android.content.Intent
import com.has.android.core.model.Image
import com.has.android.core.model.ImageFolder
import com.has.android.common.util.constants.GalleryConstants.SINGLE

data class GalleryScreenState(
    val type: String = SINGLE,
    val focusImage: Image? = null,
    val selectedFolder: ImageFolder = ImageFolder(),
    val imageList: List<Image> = emptyList(),
    val imageFolderList: List<ImageFolder> = emptyList(),
    val selectedImageList: List<Image> = emptyList()
)

sealed interface GallerySideEffect {
    object OverSelect: GallerySideEffect
    data class Complete(val intent: Intent) : GallerySideEffect
}