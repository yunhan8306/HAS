package com.myStash.android.core.model

data class Feed(
    val id: Long?,
//    val date: LocalDate,
    val images: List<String>,
    val styleId: Long?,
    val createTime: Long = System.currentTimeMillis(),
    val isRemove: Boolean = false
)