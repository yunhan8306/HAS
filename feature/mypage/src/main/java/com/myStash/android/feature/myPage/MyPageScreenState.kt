package com.myStash.android.feature.myPage

data class MyPageScreenState(
    val profile: String? = null,
    val nickName: String? = null,
    val version: String? = null
)

sealed interface MyPageSideEffect

sealed interface MyPageScreenAction {
    data class ShowWebView(val url: String): MyPageScreenAction
    data class SelectProfile(val uri: String): MyPageScreenAction
    object ShowContact: MyPageScreenAction
    object ShowManage: MyPageScreenAction
    object EditProfile: MyPageScreenAction
}