package com.myStash.android.core.model

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

fun List<StyleScreenModel>.filterSelectTag(
    tags: List<Tag>
): List<StyleScreenModel> {
    return if(tags.isEmpty()) {
        this
    } else {
        this.filter { style ->
            var isSelect = false
            style.hasList.forEachIndexed { _, has ->
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

fun Long?.getStyleScreenModel(styleList: List<StyleScreenModel>): StyleScreenModel? {
    return styleList.firstOrNull { it.id == this }
}

fun StyleScreenModel.selectOrNull(style: StyleScreenModel?): StyleScreenModel? {
    return if(id != style?.id) {
        this
    } else {
        null
    }
}