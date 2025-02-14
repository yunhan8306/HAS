package com.has.android.feature.item

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.has.android.design_system.animation.slideOut
import com.has.android.design_system.ui.theme.HasMoreTheme
import com.has.android.feature.item.item.AddItemRoute
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ItemActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            HasMoreTheme {
                AddItemRoute(
                    onBack = ::finish
                )
            }
        }
    }

    override fun onPause() {
        if (isFinishing) {
            slideOut()
        }
        super.onPause()
    }
}