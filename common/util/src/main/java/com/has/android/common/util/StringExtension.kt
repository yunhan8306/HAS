package com.has.android.common.util

fun String?.isNotNullAndNotEmpty(): Boolean =
    this != null && !this.equals("null", true) && this.isNotEmpty() && this.isNotBlank()

fun String.removeLastBlank(): String {
    return if(endsWith(" ")) {
        removeSuffix(" ")
    } else {
        this
    }
}

fun String.ifEmptyReturnNull(): String? =
    takeIf { isNotNullAndNotEmpty() }