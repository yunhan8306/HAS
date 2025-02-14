package com.has.android.feature.gallery

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.has.android.design_system.animation.slideOut
import com.has.android.design_system.ui.theme.HasGalleryTheme
import com.has.android.feature.gallery.ui.GalleryRoute
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GalleryActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            HasGalleryTheme {
                GalleryRoute()
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