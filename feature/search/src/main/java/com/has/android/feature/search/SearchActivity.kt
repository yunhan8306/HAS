package com.has.android.feature.search

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.has.android.design_system.animation.fadeOut
import com.has.android.design_system.ui.theme.HasSearchTheme
import com.has.android.feature.search.ui.SearchRoute
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            HasSearchTheme {
                SearchRoute()
            }
        }
    }

    override fun onPause() {
        if (isFinishing) {
            fadeOut()
        }
        super.onPause()
    }
}