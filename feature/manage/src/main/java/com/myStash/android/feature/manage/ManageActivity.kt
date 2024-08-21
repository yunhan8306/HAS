package com.myStash.android.feature.manage

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.myStash.android.design_system.animation.slideOut
import com.myStash.android.design_system.ui.theme.HasMoreTheme
import com.myStash.android.feature.manage.manage.ManageRoute
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ManageActivity: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            HasMoreTheme {
                ManageRoute(
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