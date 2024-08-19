package com.myStash.android.design_system.animation

import androidx.activity.ComponentActivity
import com.myStash.android.core.design_system.R

fun ComponentActivity.slideIn() {
    overridePendingTransition(R.anim.slide_in, R.anim.nothing)
}

fun ComponentActivity.slideOut() {
    overridePendingTransition(R.anim.nothing, R.anim.slide_out)
}

fun ComponentActivity.fadeIn() {
    overridePendingTransition(R.anim.fade_in, R.anim.nothing)
}

fun ComponentActivity.fadeOut() {
    overridePendingTransition(R.anim.nothing, R.anim.fade_out)
}