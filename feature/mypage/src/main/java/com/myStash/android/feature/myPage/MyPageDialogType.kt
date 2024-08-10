package com.myStash.android.feature.myPage

sealed interface MyPageDialogType {
    data class WebView(val url: String) : MyPageDialogType
    object Contact: MyPageDialogType
}