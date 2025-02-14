package com.has.android.feature.myPage.ui

sealed interface MyPageMenuType {
    data class WebView(val url: String) : MyPageMenuType
    object Contact: MyPageMenuType
    object Manage: MyPageMenuType
}