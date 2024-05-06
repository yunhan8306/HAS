package com.myStash.feature.item

import android.content.Intent
import androidx.activity.ComponentActivity

fun ComponentActivity.launchItemActivity() {
    Intent(this, ItemActivity::class.java).apply {
        startActivity(this)
    }
}