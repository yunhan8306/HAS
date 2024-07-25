package com.myStash.android.feature.item.has

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
import com.myStash.android.feature.gallery.permission.GalleryPermission
import com.myStash.android.feature.gallery.permission.PermissionUtil.galleryPermissionCheck
import com.myStash.android.feature.search.TagSearchBottomSheetLayout
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun AddHasRoute(
    viewModel: AddHasViewModel = hiltViewModel(),
    finishActivity: () -> Unit
) {

    val activity = LocalContext.current as ComponentActivity
    val galleryActivityLauncher = rememberLauncherForActivityResult(
        contract = CommonActivityResultContract(),
        onResult = { intent ->
            intent?.getStringExtra("imageUri")?.let { viewModel.addImage(it) }
        }
    )
    val requestPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            val intent = Intent(activity.apply { slideIn() }, GalleryActivity::class.java)
            galleryActivityLauncher.launch(intent)
        }
    }

    val state by viewModel.collectAsState()
    val searchModalState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden, skipHalfExpanded = true)
    val scope = rememberCoroutineScope()
    var isShowPermissionRequestConfirm by remember { mutableStateOf(false) }

    viewModel.collectSideEffect { sideEffect ->
        when(sideEffect) {
            AddHasSideEffect.Finish -> finishActivity.invoke()
            else -> Unit
        }
    }

    AddHasScreen(
        imageUri = state.imageUri,
        selectedType = state.selectedType,
        typeTotalList = state.typeTotalList,
        selectType = viewModel::selectType,
        selectedTagList = state.selectedTagList,
        selectTag = viewModel::selectTag,
        search = {
            viewModel.deleteSearchText()
            scope.launch { searchModalState.show() }
        },
        saveItem = viewModel::saveItem,
        showGalleryActivity = {
            activity.galleryPermissionCheck(
                permissions = GalleryPermission.GALLERY_PERMISSIONS,
                requestPermissionLauncher = requestPermissionLauncher,
                onPermissionDenied = { isShowPermissionRequestConfirm = true }
            )
        },
        onBack = finishActivity,
        searchModalState = searchModalState,
        sheetContent = {
            TagSearchBottomSheetLayout(
                searchModalState = searchModalState,
                searchTextState = viewModel.searchTextState,
                totalTagList = state.tagTotalList,
                selectTagList = state.selectedTagList,
                searchTagList = state.searchTagList,
                buttonText = "완료",
                onSelect = viewModel::selectTag,
                onConfirm = { scope.launch { searchModalState.hide() } },
                onDelete = viewModel::deleteSearchText,
                onBack = { scope.launch { searchModalState.hide() }
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