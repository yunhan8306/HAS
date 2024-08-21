package com.myStash.android.feature.myPage

data class MyPageScreenState(
    val nickName: String = "",
)

sealed interface MyPageSideEffect

sealed interface MyPageScreenAction {
    data class ShowWebView(val url: String) : MyPageScreenAction
    object ShowContact: MyPageScreenAction
    object ShowManage: MyPageScreenAction
}