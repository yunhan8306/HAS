package com.myStash.android.feature.item

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.myStash.android.design_system.animation.slideOut
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ItemActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ItemEssentialRoute(
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