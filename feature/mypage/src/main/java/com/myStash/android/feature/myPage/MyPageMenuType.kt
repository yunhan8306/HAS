package com.myStash.android.feature.myPage

sealed interface MyPageMenuType {
    data class WebView(val url: String) : MyPageMenuType
    object Contact: MyPageMenuType
    object Manage: MyPageMenuType
}