package com.myStash.android.core.model

data class Style(
    val id: Long? = null,
    val hass: List<Long> = emptyList(),
    val createTime: Long = System.currentTimeMillis(),
    val isRemove: Boolean = false
)