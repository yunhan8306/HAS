package com.myStash.android.feature.item.feed

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import com.myStash.android.feature.gallery.GalleryConstants
import com.myStash.android.feature.gallery.permission.GalleryPermission
import com.myStash.android.feature.gallery.permission.PermissionUtil.galleryPermissionCheck
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun AddFeedRoute(
    viewModel: AddFeedViewModel = hiltViewModel(),
    finishActivity: () -> Unit
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

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            val intent = Intent(activity.apply { slideIn() }, GalleryActivity::class.java).apply {
                putExtra(GalleryConstants.TYPE, GalleryConstants.MULTI
                )
                putExtra(GalleryConstants.AGO_IMAGE_URI_ARRAY, state.selectedImageList.toTypedArray())
            }
            galleryActivityLauncher.launch(intent)
        }
    }

    viewModel.collectSideEffect { sideEffect ->
        when(sideEffect) {
            AddFeedSideEffect.Finish -> finishActivity.invoke()
            else -> Unit
        }
    }

    AddFeedScreen(
        searchModalState = selectStyleModalState,
        state = state,
//        selectedHasList = state.selectedHasList,
//        saveItem = viewModel::saveFeed,
        showGalleryActivity = {
            activity.galleryPermissionCheck(
                permissions = GalleryPermission.GALLERY_PERMISSIONS,
                requestPermissionLauncher = requestPermissionLauncher,
                onPermissionDenied = { isShowPermissionRequestConfirm = true }
            )
        },
        showStyleSheet = {
            scope.launch { selectStyleModalState.show() }
        },
        sheetContent = {
            SelectStyleModalSheet(
                selectStyleModalState = selectStyleModalState,
                state = state,
                searchTextState = viewModel.searchTextState,

                totalTagList = state.tagTotalList,
                selectTagList = state.selectedTagList,
                searchTagList = state.searchTagList,
                buttonText = "완료",
                onSelect = viewModel::selectTag,
                onConfirm = { scope.launch { selectStyleModalState.hide() } },
                onDelete = viewModel::deleteSearchText,
                onBack = { scope.launch { selectStyleModalState.hide() } }
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