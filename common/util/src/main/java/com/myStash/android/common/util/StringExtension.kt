package com.myStash.android.common.util

fun String?.isNotNullAndNotEmpty(): Boolean =
    this != null && !this.equals("null", true) && this.isNotEmpty() && this.isNotBlank()