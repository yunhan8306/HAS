package com.myStash.feature.gallery

import android.Manifest
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.core.app.ActivityCompat
import androidx.lifecycle.lifecycleScope
import com.myStash.design_system.ui.theme.NoRippleTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.compose.collectAsState

@AndroidEntryPoint
class GalleryActivity : ComponentActivity() {
    private val viewModel by viewModels<GalleryViewModel>()

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.READ_MEDIA_IMAGES),
            0
        )

        setContent {
            GalleryRoute(complete = ::complete)
        }
    }

    private fun complete() {
        val intent = Intent().apply { putExtra("imageUri", viewModel.selectedList[0].uri.toString()) }
        setResult(RESULT_OK, intent)
        finish()
    }
}