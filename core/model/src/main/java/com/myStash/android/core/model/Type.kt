package com.myStash.android.core.model

class Type(
    val id: Long? = null,
    val name: String = ""
)

val testTypeTotalList = listOf(
    Type(id = 0,name = "전체"),
    Type(id = 1,name = "상의"),
    Type(id = 2,name = "아우터"),
    Type(id = 3,name = "팬츠"),
    Type(id = 4,name = "스커트"),
    Type(id = 5,name = "원피스"),
    Type(id = 6,name = "신발"),
)