package com.myStash.android.feature.myPage

data class MyPageScreenState(
    val profile: String? = null,
    val nickName: String = "",
    val version: String? = null
)

sealed interface MyPageSideEffect

sealed interface MyPageScreenAction {
    data class EditProfileUri(val uri: String): MyPageScreenAction
    data class ShowWebView(val url: String): MyPageScreenAction
    object ShowContact: MyPageScreenAction
    object ShowManage: MyPageScreenAction
    object ShowGalleryProfile: MyPageScreenAction
}