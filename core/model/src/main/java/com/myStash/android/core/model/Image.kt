package com.myStash.android.core.model

import android.net.Uri

data class Image(
    val id: Long,
    val name: String,
    val uri: Uri,
    val folderId: Long,
    val folder: String,
    var isSelected: Boolean = false,
) {
    fun select() {
        isSelected = !isSelected
    }
}