package com.myStash.feature.item

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResult
import com.myStash.core.model.Item
import com.myStash.design_system.animation.slideOut
import com.myStash.feature.gallery.launchGalleryTestActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.MutableSharedFlow

@AndroidEntryPoint
class ItemActivity : ComponentActivity() {

    private val selectedPhotoSharedFlow = MutableSharedFlow<String?>(replay = 1)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ItemRoute(
                addImageSharedFlow = selectedPhotoSharedFlow,
                showGalleryActivity = { launchGalleryTestActivity(::galleryActivityCallback) },
                finishActivity = ::finish
            )
        }
    }

    private fun galleryActivityCallback(activityCallback: ActivityResult) {
        activityCallback.data?.getStringExtra("imageUri")?.let {
            selectedPhotoSharedFlow.tryEmit(it)
        }
    }

    override fun onPause() {
        if (isFinishing) {
            slideOut()
        }
        super.onPause()
    }
}