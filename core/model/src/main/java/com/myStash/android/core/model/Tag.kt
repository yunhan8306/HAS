package com.myStash.android.core.model

data class Tag(
    val id: Long? = null,
    val name: String,
    val isBrand: Boolean = false
) {
    override fun equals(other: Any?): Boolean {
        return name == (other as Tag).name
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