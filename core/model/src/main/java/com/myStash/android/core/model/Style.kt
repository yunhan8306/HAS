package com.myStash.android.core.model

import com.myStash.android.core.model.Style.Companion.toStyleScreenModel

data class Style(
    val id: Long? = null,
    val hass: List<Long> = emptyList(),
    val createTime: Long = System.currentTimeMillis(),
    val isRemove: Boolean = false
) {
    companion object {
        fun Style.toStyleScreenModel(hasTotalList: List<Has>) = StyleScreenModel(
            id = id!!,
            hasList = hasTotalList.filter { hass.contains(it.id) }
        )
    }
}

data class StyleScreenModel(
    val id: Long,
    val hasList: List<Has>
)

fun List<Style>.filterSelectTag(
    tags: List<Tag>,
    hasList: List<Has>
): List<Style> {
    return if(tags.isEmpty()) {
        this
    } else {
        this.filter { style ->
            var isSelect = false
            style.toStyleScreenModel(hasList).hasList.forEachIndexed { _, has ->
                tags.forEach { tag ->
                    if(has.tags.contains(tag.id)) {
                        isSelect = true
                        return@forEach
                    }
                }
                if(isSelect) return@forEachIndexed
            }
            isSelect
        }
    }
}