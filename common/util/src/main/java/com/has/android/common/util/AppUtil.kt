package com.has.android.common.util

import android.content.Context

fun Context.getAppVersion(): String? {
    return try {
        this.packageManager.getPackageInfo(this.packageName, 0).versionName
    } catch (e: Exception) {
        null
    }
}