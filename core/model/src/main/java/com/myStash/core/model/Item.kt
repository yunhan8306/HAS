package com.myStash.core.model

data class Item(
    val id: Long? = null,
    val name: String,
    val tags: List<Long> = emptyList(),
    val brand: Long? = null,
    val type: String? = null,
    val imagePath: String? = null,
    val memo: String,
    val createTime: Long = System.currentTimeMillis(),
    val isRemove: Boolean = false
)