package com.myStash.core.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Item(
    val id: Long? = null,
    val name: String = "",
    val tags: List<Long> = emptyList(),
    val brand: Long? = null,
    val type: String? = null,
    val imagePath: String? = null,
    val memo: String = "",
    val createTime: Long = System.currentTimeMillis(),
    val isRemove: Boolean = false
): Parcelable {
    fun getTagList(tagTotalList: List<Tag>) = tagTotalList.filter { tags.contains(it.id) }
    fun getBrand(tagTotalList: List<Tag>) = tagTotalList.firstOrNull { brand == it.id }

}