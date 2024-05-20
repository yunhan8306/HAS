package com.myStash.feature.item

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.myStash.design_system.animation.slideOut
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ItemActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ItemRoute(
                finishActivity = ::finish
            )
        }
    }

    override fun onPause() {
        if (isFinishing) {
            slideOut()
        }
        super.onPause()
    }
}