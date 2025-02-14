package com.myStach.android.feature.contact

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.myStach.android.feature.contact.ui.ContactRoute
import com.has.android.design_system.animation.slideOut
import com.has.android.design_system.ui.theme.HasMoreTheme
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