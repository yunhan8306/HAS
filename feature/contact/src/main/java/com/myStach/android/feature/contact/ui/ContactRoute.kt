package com.myStach.android.feature.contact.ui

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
import com.myStach.android.feature.contact.ContactViewModel
import com.myStash.android.common.compose.AppLifecycleObserver
import com.myStash.android.common.compose.LifecycleEventObserver
import com.myStash.android.common.util.CommonActivityResultContract
import com.myStash.android.common.util.constants.PermissionConstants
import com.myStash.android.design_system.animation.slideIn
import com.myStash.android.design_system.ui.component.dialog.HasConfirmDialog
import com.myStash.android.design_system.util.rememberPermissionLauncher
import com.myStash.android.feature.gallery.GalleryActivity
import com.myStash.android.common.util.constants.GalleryConstants
import kotlinx.coroutines.launch
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
    var isShowContactCompleteConfirm by remember { mutableStateOf(false) }
    var contactCompleteStep by remember { mutableStateOf(ContactCompleteStep.NONE) }

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
            val intent = Intent(activity, GalleryActivity::class.java).apply {
                putExtra(GalleryConstants.TYPE, GalleryConstants.MULTI)
                putExtra(GalleryConstants.AGO_IMAGE_URI_ARRAY, state.selectedImages.toTypedArray())
            }
            galleryActivityLauncher.launch(intent)
            activity.slideIn()
        },
        denied = {
            isShowPermissionRequestConfirm = true
        }
    )

    viewModel.collectSideEffect { sideEffect ->
        when(sideEffect) {
            is ContactSideEffect.SendEmail -> {
                scope.launch {
                    contactCompleteStep = ContactCompleteStep.COMPLETE
                    activity.startActivity(sideEffect.intent)
                }
            }
            else -> Unit
        }
    }

    AppLifecycleObserver(
        onBackground = {
            contactCompleteStep =
                if(contactCompleteStep == ContactCompleteStep.COMPLETE) ContactCompleteStep.TRY
                else ContactCompleteStep.NONE
        },
        onForeground = {
            contactCompleteStep =
                if(contactCompleteStep == ContactCompleteStep.TRY) ContactCompleteStep.END
                else ContactCompleteStep.NONE
        }
    )

    LifecycleEventObserver(
        onResume = {
            if(contactCompleteStep == ContactCompleteStep.END)
                isShowContactCompleteConfirm = true
            else ContactCompleteStep.NONE
        }
    )

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

    HasConfirmDialog(
        isShow = isShowContactCompleteConfirm,
        title = "문의 확인",
        content = "문의를 완료하셨나요?",
        confirmText = "확인",
        dismissText = "취소",
        onConfirm = { activity.finish() },
        onDismiss = { isShowContactCompleteConfirm = false }
    )
}