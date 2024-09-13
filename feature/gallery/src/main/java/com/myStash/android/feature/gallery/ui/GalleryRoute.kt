package com.myStash.android.feature.gallery.ui

import android.app.Activity
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.myStash.android.common.compose.LifecycleEventObserver
import com.myStash.android.common.util.constants.PermissionConstants
import com.myStash.android.feature.gallery.ui.component.GalleryFolderWindow
import com.myStash.android.feature.gallery.ui.component.GalleryModalSheet
import com.myStash.android.feature.gallery.GalleryViewModel
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun GalleryRoute(
    viewModel: GalleryViewModel = hiltViewModel()
) {
    val activity = LocalContext.current as ComponentActivity
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberBottomSheetScaffoldState()
    var isShowFolderWindow by remember { mutableStateOf(false) }
    val state by viewModel.collectAsState()

    viewModel.collectSideEffect { sideEffect ->
        when(sideEffect) {
            is GallerySideEffect.OverSelect -> {
                scope.launch {
                    scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
                    scaffoldState.snackbarHostState.showSnackbar("사진 갯수 초과")
                }
            }
            is GallerySideEffect.Complete -> {
                activity.apply {
                    setResult(Activity.RESULT_OK, sideEffect.intent)
                    finish()
                }
            }
        }
    }

    LifecycleEventObserver(onResume = viewModel::getGalleryImages)

    GalleryScreen(
        scaffoldState = scaffoldState,
        focusImage = state.focusImage,
        onAction = viewModel::onAction,
        onBack = { activity.finish() },
        selectedFolder = state.selectedFolder,
        isShowFolderWindow = isShowFolderWindow,
        onSelectFolder = { isShowFolderWindow = !isShowFolderWindow },
        sheetContent = {
            GalleryModalSheet(
                state = state,
                onAction = viewModel::onAction,
                onRequestPermission = { activity.requestPermissions(PermissionConstants.GALLERY_PERMISSIONS, 0) }
            )
        }
    )

    GalleryFolderWindow(
        isShow = isShowFolderWindow,
        imageFolderList = state.imageFolderList,
        onAction = viewModel::onAction,
        dismiss = { isShowFolderWindow = false },
    )

    BackHandler {
        when {
            isShowFolderWindow -> {
                isShowFolderWindow = false
            }
            scaffoldState.bottomSheetState.targetValue == BottomSheetValue.Expanded -> {
                scope.launch { scaffoldState.bottomSheetState.collapse() }
            }
            else -> activity.finish()
        }
    }
}