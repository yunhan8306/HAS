package com.myStash.android.core.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Item(
    val id: Long? = null,
    val tags: List<Long> = emptyList(),
    val type: Long,
    val imagePath: String? = null,
    val createTime: Long = System.currentTimeMillis(),
    val isRemove: Boolean = false
): Parcelable {
    fun getTagList(tagTotalList: List<Tag>) = tagTotalList.filter { tags.contains(it.id) }
}