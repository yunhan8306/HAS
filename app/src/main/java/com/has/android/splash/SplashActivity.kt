package com.has.android.splash

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.has.android.common.util.setImmersiveMode
import com.has.android.design_system.ui.theme.HasSearchTheme
import com.has.android.design_system.ui.theme.HasSplashTheme
import dagger.hilt.android.AndroidEntryPoint

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setImmersiveMode()
        setContent {
            HasSplashTheme {
                SplashRoute()
            }
        }
    }
}