package com.myStash.android.feature.item.feed

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
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
import com.myStash.android.common.util.constants.GalleryConstants
import com.myStash.android.common.util.constants.PermissionConstants
import com.myStash.android.design_system.util.rememberPermissionLauncher
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun AddFeedRoute(
    viewModel: AddFeedViewModel = hiltViewModel()
) {

    val activity = LocalContext.current as ComponentActivity
    val state by viewModel.collectAsState()
    val selectStyleModalState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden, skipHalfExpanded = true)
    val scope = rememberCoroutineScope()
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
            val intent = Intent(activity, GalleryActivity::class.java).apply {
                putExtra(GalleryConstants.TYPE, GalleryConstants.MULTI)
                putExtra(GalleryConstants.AGO_IMAGE_URI_ARRAY, state.selectedImageList.toTypedArray())
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
            is AddFeedSideEffect.Finish -> {
                activity.apply {
                    setResult(Activity.RESULT_OK, sideEffect.intent)
                    finish()
                }
            }
            else -> Unit
        }
    }

    AddFeedScreen(
        searchModalState = selectStyleModalState,
        state = state,
//        selectedHasList = state.selectedHasList,
//        saveItem = viewModel::saveFeed,
        showGalleryActivity = {
            requestPermissionLauncher.launch(PermissionConstants.GALLERY_PERMISSIONS)
        },
        showStyleSheet = {
            scope.launch { selectStyleModalState.show() }
        },
        onAction = { action ->
            when(action) {
                else -> viewModel.onAction(action)
            }
        },
        sheetContent = {
            SelectStyleModalSheet(
                selectStyleModalState = selectStyleModalState,
                state = state,
                searchTextState = viewModel.searchTextState,

                totalTagList = state.tagList,
                selectTagList = state.selectedTagList,
                searchTagList = state.tagList,
                onBack = { scope.launch { selectStyleModalState.hide() } },


                onAction = { action ->
                    when(action) {
                        is AddFeedScreenAction.SelectStyle -> {
                            scope.launch {
                                viewModel.onAction(action)
                                selectStyleModalState.hide()
                            }
                        }
                        else -> viewModel.onAction(action)
                    }
                }
            )
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