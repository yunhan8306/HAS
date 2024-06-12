package com.myStash.android.core.model

data class Style(
    val id: Long? = null,
    val imagePaths: List<String> = emptyList(),
    val items: List<Long> = emptyList(),
    val createTime: Long = System.currentTimeMillis(),
    val isRemove: Boolean = false
)