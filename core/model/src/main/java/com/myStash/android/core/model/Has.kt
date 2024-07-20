package com.myStash.android.core.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Has(
    val id: Long? = null,
    val tags: List<Long> = emptyList(),
    val type: Long,
    val imagePath: String? = null,
    val createTime: Long = System.currentTimeMillis(),
    val isRemove: Boolean = false
): Parcelable {
    fun getTagList(tagTotalList: List<Tag>) = tagTotalList.filter { tags.contains(it.id) }

    fun checkSelected(hasList: List<Has>): Boolean {
        hasList.forEach {
            if (it.id == this.id) return true
        }
        return false
    }
}
fun List<Has>.selectType(type: Type): List<Has> {
    return if(type.id?.toInt() == 0) {
        this
    } else {
        this.filter { it.type == type.id }
    }
}

fun List<Has>.selectTag(tags: List<Tag>): List<Has> {
    val selectedTagsId = tags.map { it.id!! }
    return if(tags.isEmpty()) {
        this
    } else {
        this.filter { has ->
            var isSelected = false
            has.tags.forEach { tagId ->
                if(selectedTagsId.contains(tagId)) {
                    isSelected = true
                    return@forEach
                }
            }
            isSelected
        }
    }
}
