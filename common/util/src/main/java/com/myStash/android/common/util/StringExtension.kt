package com.myStash.android.common.util

fun String?.isNotNullAndNotEmpty(): Boolean =
    this != null && !this.equals("null", true) && this.isNotEmpty() && this.isNotBlank()

fun String.removeLastBlank(): String {
    return if(endsWith(" ")) {
        removeSuffix(" ")
    } else {
        this
    }
}