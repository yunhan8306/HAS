package com.myStash.android.core.model

import java.time.LocalDate

data class Feed(
    val id: Long? = null,
    val date: LocalDate,
    val images: List<String>,
    val styleId: Long?,
    val createTime: Long = System.currentTimeMillis(),
    val isRemove: Boolean = false
)

fun List<Feed>.filterDate(year: Int, month: Int): List<Feed> {
    return filter { feed -> feed.date.year == year && feed.date.monthValue == month }
}

fun List<Feed>.getFeedByDate(date: LocalDate): Feed? {
    return firstOrNull { it.date == date }
}