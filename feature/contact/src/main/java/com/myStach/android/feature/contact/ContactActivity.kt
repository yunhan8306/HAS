package com.myStach.android.feature.contact

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.myStash.android.design_system.animation.slideOut
import com.myStash.android.design_system.ui.theme.HasDefaultTheme
import com.myStash.android.design_system.ui.theme.HasMoreTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContactActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HasMoreTheme {
                ContactRoute()
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