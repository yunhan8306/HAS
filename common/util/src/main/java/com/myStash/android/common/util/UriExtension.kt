package com.myStash.android.common.util

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File

fun String.getUri(context: Context): Uri? {
    return when {
        startsWith("content://") || startsWith("file://") -> {
            Uri.parse(this)
        }
        else -> {
            val file = File(this)
            FileProvider.getUriForFile(
                context,
                "${context.packageName}.provider",
                file
            )
        }
    }
}