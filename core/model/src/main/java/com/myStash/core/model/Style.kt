package com.myStash.core.model

data class Style(
    val id: Long? = null,
    val imagePath: String? = null,
    val items: List<Long> = emptyList(),
    val memo: String
)