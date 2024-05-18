package com.myStash.feature.item

import android.content.Intent
import androidx.activity.ComponentActivity
import com.myStash.core.model.Item
import com.myStash.design_system.animation.slideIn

fun ComponentActivity.launchItemActivity(item: Item?) {
    Intent(this, ItemActivity::class.java).apply {
        putExtra("item", item)
        startActivity(this)
        slideIn()
    }
}