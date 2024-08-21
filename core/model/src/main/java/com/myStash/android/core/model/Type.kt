package com.myStash.android.core.model

data class Type(
    val id: Long? = null,
    val name: String = "",
    val order: Long? = 99,
    val createTime: Long = System.currentTimeMillis(),
    val isRemove: Boolean = false
)

fun List<Type>.update(type: Type): List<Type> {
    return this.map {
        if(type.id == it.id) {
            type
        } else {
            it
        }
    }.toList()
}

fun getTotalType() = Type(id = null, name = "전체")

fun getTotalTypeList() = listOf(getTotalType())

val testManTypeTotalList = listOf(
    Type(id = 1,name = "상의"),
    Type(id = 2,name = "아우터"),
    Type(id = 3,name = "팬츠"),
    Type(id = 4,name = "신발"),
)

val testWomanTypeTotalList = listOf(
    Type(id = 1,name = "상의"),
    Type(id = 7,name = "스커트"),
    Type(id = 8,name = "원피스"),
    Type(id = 2,name = "아우터"),
    Type(id = 3,name = "팬츠"),
    Type(id = 6,name = "신발"),
)