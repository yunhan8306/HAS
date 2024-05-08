package com.myStash.core.model

import android.net.Uri

data class Image(
    val id: Long,
    val name: String,
    val uri: Uri,
    var isSelected: Boolean = false,
) {
    fun select() {
        isSelected = !isSelected
    }
}