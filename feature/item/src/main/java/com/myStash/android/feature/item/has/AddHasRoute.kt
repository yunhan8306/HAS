package com.myStash.android.feature.item.has

import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.myStash.android.common.util.CommonActivityResultContract
import com.myStash.android.design_system.animation.slideIn
import com.myStash.android.feature.gallery.GalleryActivity
import com.myStash.android.feature.item.EssentialItemSideEffect
import com.myStash.android.feature.item.ItemEssentialViewModel
import com.myStash.android.feature.search.TagSearchBottomSheetLayout
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AddHasRoute(
    viewModel: ItemEssentialViewModel = hiltViewModel(),
    finishActivity: () -> Unit
) {

    val activity = LocalContext.current as ComponentActivity
    val galleryActivityLauncher = rememberLauncherForActivityResult(
        contract = CommonActivityResultContract(),
        onResult = { intent ->
            intent?.getStringExtra("imageUri")?.let { viewModel.addImage(it) }
        }
    )

    val state by viewModel.collectAsState()
    val searchModalState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden, skipHalfExpanded = true)
    val scope = rememberCoroutineScope()

    viewModel.collectSideEffect { sideEffect ->
        when(sideEffect) {
            EssentialItemSideEffect.Finish -> finishActivity.invoke()
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
            val intent = Intent(activity.apply { slideIn() }, GalleryActivity::class.java)
            galleryActivityLauncher.launch(intent)
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
}