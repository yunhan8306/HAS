package com.myStash.android.feature.gallery

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GalleryActivity : ComponentActivity() {
    private val viewModel by viewModels<GalleryViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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