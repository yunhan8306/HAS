package com.myStash.feature.item

import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.myStash.common.util.CommonActivityResultContract
import com.myStash.design_system.animation.slideIn
import com.myStash.feature.gallery.GalleryActivity
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun ItemRoute(
    viewModel: ItemViewModel = hiltViewModel(),
    finishActivity: () -> Unit
) {

    val state by viewModel.collectAsState()
    val tagInputState by remember { mutableStateOf(viewModel.addTagState) }

    val activity = LocalContext.current as ComponentActivity
    val galleryActivityLauncher = rememberLauncherForActivityResult(
        contract = CommonActivityResultContract(),
        onResult = { intent ->
            intent?.getStringExtra("imageUri")?.let { viewModel.addImage(it) }
        }
    )

    viewModel.collectSideEffect { sideEffect ->
        when(sideEffect) {
            ItemSideEffect.Finish -> finishActivity.invoke()
            else -> Unit
        }
    }

    ItemScreen(
        state = state,
        tagInputState = tagInputState,
        saveItem = viewModel::saveItem,
        showGalleryActivity = {
            val intent = Intent(activity.apply { slideIn() }, GalleryActivity::class.java)
            galleryActivityLauncher.launch(intent)
        }
    )
}