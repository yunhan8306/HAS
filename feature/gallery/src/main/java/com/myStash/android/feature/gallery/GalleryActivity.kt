package com.myStash.android.feature.gallery

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.myStash.android.design_system.animation.slideOut
import com.myStash.android.design_system.ui.theme.HasGalleryTheme
import com.myStash.android.feature.gallery.ui.GalleryRoute
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