package com.myStash.android.core.model

import com.myStash.android.common.util.removeLastBlank

data class Tag(
    val id: Long? = null,
    val name: String,
    val createTime: Long = System.currentTimeMillis(),
    val isRemove: Boolean = false
) {
    private var _usedCnt = 0
    val usedCnt: Int
        get() = _usedCnt

    fun setUsedCnt(cnt: Int) {
        _usedCnt = cnt
    }

    fun checkSelected(tagList: List<Tag>): Boolean {
        tagList.forEach { tag ->
            if(id == tag.id) return true
        }
        return false
    }
}

fun List<Tag>.sortSelectedTagList(selectedTagList: List<Tag>, isFold: Boolean): List<Tag> {
    return if(isFold) {
        selectedTagList + filter { !it.checkSelected(selectedTagList) }
    } else {
        this
    }
}

fun List<Long>.getTagList(tagList: List<Tag>): List<Tag> {
    return tagList.filter { contains(it.id) }
}

fun List<Tag>.update(tag: Tag): List<Tag> {
    return this.map {
        if(tag.id == it.id) {
            tag
        } else {
            it
        }
    }.toList()
}

fun List<Tag>.setUsedHasCnt(hasTotalList: List<Has>): List<Tag> {
    return map { tag -> tag.apply { setUsedCnt(hasTotalList.filter { it.tags.contains(tag.id) }.size) } }
}

fun List<Tag>.setUsedStyleCnt(styleTotalList: List<StyleScreenModel>): List<Tag> {
    return map { tag ->
        tag.apply {
            setUsedCnt(
                styleTotalList.filter {
                    var isUsed = false
                    it.hasList.onEach {  has ->
                        if(has.tags.contains(tag.id)) {
                            isUsed = true
                            return@onEach
                        }
                    }
                    isUsed
                }.size
            )
        }
    }
}

fun List<Tag>.tagFoundFromSearchText(text: CharSequence, found: (Tag) -> Unit, complete: (String) -> Unit) {
    if(text.contains(" ")) {
        var emptyText = ""
        text.split(" ").forEach { tagText ->
            if(tagText.isBlank()) return@forEach
            this.onEachIndexed { index, tag ->
                if(tag.name == tagText) {
                    found.invoke(tag)
                    return@forEach
                }
                if(index == this.lastIndex) emptyText += "$tagText "
            }
        }
        complete.invoke(emptyText.removeLastBlank())
    }
}

fun List<Tag>.tagAddAndFoundFromSearchText(text: CharSequence, add: (String) -> Unit, found: (Tag) -> Unit, complete: (String) -> Unit) {
    if(text.contains(" ")) {
        var emptyText = ""
        text.split(" ").forEach { tagText ->
            if(tagText.isBlank()) return@forEach

            this.find { it.name == tagText }?.let {
                found.invoke(it)
            } ?: run {
                if(tagText.length >= 2) add.invoke(tagText) else emptyText += tagText
            }
        }
        complete.invoke(emptyText.removeLastBlank())
    }
}

val testTagList = listOf(
    Tag(name = "nike"),
    Tag(name = "needles"),
    Tag(name = "namacheko"),
    Tag(name = "고프코어"),
    Tag(name = "나이키"),
    Tag(name = "미니멀"),
    Tag(name = "바람막이"),
    Tag(name = "카고"),
    Tag(name = "질샌더"),
    Tag(name = "르메르"),
    Tag(name = "아워레가시"),
    Tag(name = "자크뮈스"),
    Tag(name = "카사블랑카"),
    Tag(name = "오라리"),
)