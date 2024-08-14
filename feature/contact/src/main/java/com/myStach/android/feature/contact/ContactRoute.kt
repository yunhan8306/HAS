package com.myStach.android.feature.contact

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.myStash.android.common.util.CommonActivityResultContract
import com.myStash.android.design_system.animation.slideIn
import com.myStash.android.design_system.ui.component.dialog.HasConfirmDialog
import com.myStash.android.feature.gallery.GalleryActivity
import com.myStash.android.feature.gallery.GalleryConstants
import com.myStash.android.common.util.constants.PermissionConstants
import com.myStash.android.design_system.util.rememberPermissionLauncher
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun ContactRoute(
    viewModel: ContactViewModel = hiltViewModel()
) {

    val scope = rememberCoroutineScope()
    val activity = LocalContext.current as ComponentActivity
    val state by viewModel.collectAsState()
    var isShowPermissionRequestConfirm by remember { mutableStateOf(false) }

    val galleryActivityLauncher = rememberLauncherForActivityResult(
        contract = CommonActivityResultContract(),
        onResult = { intent ->
            intent?.getStringArrayExtra(GalleryConstants.MULTI)?.let { viewModel.addImageList(it.toList()) }
        }
    )

    val requestPermissionLauncher = rememberPermissionLauncher(
        activity = activity,
        scope = scope,
        grant = {
            val intent = Intent(activity.apply { slideIn() }, GalleryActivity::class.java).apply {
                putExtra(GalleryConstants.TYPE, GalleryConstants.MULTI)
                putExtra(GalleryConstants.AGO_IMAGE_URI_ARRAY, state.selectedImages.toTypedArray())
            }
            galleryActivityLauncher.launch(intent)
        },
        denied = {
            isShowPermissionRequestConfirm = true
        }
    )

    viewModel.collectSideEffect { sideEffect ->
        when(sideEffect) {
            ContactSideEffect.Finish -> {
                activity.finish()
            }
            else -> Unit
        }
    }

    ContactScreen(
        state = state,
        contentTextState = viewModel.contentTextState,
        emailTextState = viewModel.emailTextState,
        onAction = { action ->
            when(action) {
                is ContactAction.ShowGalleryActivity -> {
                    requestPermissionLauncher.launch(PermissionConstants.GALLERY_PERMISSIONS)
                }
                is ContactAction.Finish -> {
                    activity.finish()
                }
                else -> viewModel.onAction(action)
            }
        }
    )

    HasConfirmDialog(
        isShow = isShowPermissionRequestConfirm,
        title = "권한 필요",
        content = "권한 필요",
        confirmText = "확인",
        onConfirm = {
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                data = Uri.parse("package:${activity.packageName}")
            }
            activity.startActivity(intent)
            isShowPermissionRequestConfirm = false
        }
    )
}