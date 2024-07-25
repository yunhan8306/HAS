package com.myStash.android.feature.gallery

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.myStash.android.common.compose.LifecycleEventObserver
import com.myStash.android.feature.gallery.permission.GalleryPermission
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun GalleryRoute(
    viewModel: GalleryViewModel = hiltViewModel(),
    complete: () -> Unit
) {
    val activity = LocalContext.current as ComponentActivity

    LifecycleEventObserver(onResume = viewModel::getGalleryImages)

    val state by viewModel.collectAsState()

    viewModel.collectSideEffect { sideEffect ->

    }

    GalleryScreen(
        focusImage = state.focusImage,
        complete = complete,
        onBack = { activity.finish() },
        sheetContent = {
            GalleryModalSheet(
                imageList = state.imageList,
                selectedImageList = state.selectedImageList,
                onAction = viewModel::onAction,
                onRequestPermission = { activity.requestPermissions(GalleryPermission.GALLERY_PERMISSIONS, 0) }
            )
        }
    )
}