package com.myStash.design_system.animation

import androidx.activity.ComponentActivity
import com.myStash.design_system.R

fun ComponentActivity.slideIn() {
    overridePendingTransition(R.anim.slide_in, R.anim.nothing)
}

fun ComponentActivity.slideOut() {
    overridePendingTransition(R.anim.nothing, R.anim.slide_out)
}