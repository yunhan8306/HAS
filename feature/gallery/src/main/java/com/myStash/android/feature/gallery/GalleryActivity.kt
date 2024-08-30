package com.myStash.android.feature.gallery

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.myStash.android.design_system.ui.theme.HasGalleryTheme
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
}