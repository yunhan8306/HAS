package com.myStash.android.feature.item

import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.text2.input.rememberTextFieldState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.myStash.android.common.util.CommonActivityResultContract
import com.myStash.android.design_system.animation.slideIn
import com.myStash.android.feature.gallery.GalleryActivity
import com.myStash.android.feature.search.SearchScreen
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ItemEssentialRoute(
    viewModel: ItemEssentialViewModel = hiltViewModel(),
    finishActivity: () -> Unit
) {

    val state by viewModel.collectAsState()
    var showSearchScreen by remember { mutableStateOf(false) }

    val activity = LocalContext.current as ComponentActivity
    val galleryActivityLauncher = rememberLauncherForActivityResult(
        contract = CommonActivityResultContract(),
        onResult = { intent ->
            intent?.getStringExtra("imageUri")?.let { viewModel.addImage(it) }
        }
    )

    viewModel.collectSideEffect { sideEffect ->
        when(sideEffect) {
            EssentialItemSideEffect.Finish -> finishActivity.invoke()
            else -> Unit
        }
    }

    var test by remember {
        mutableStateOf(false)
    }

    val modalState = rememberModalBottomSheetState(ModalBottomSheetValue.Expanded, skipHalfExpanded = true)

    ItemEssentialScreen(
        imageUri = state.imageUri,
        typeInputState = viewModel.typeTextState,
        tagInputState = viewModel.searchTextState,
        selectedType = state.selectedType,
        typeTotalList = state.typeTotalList,
        selectType = viewModel::selectType,
        selectedTagList = state.selectedTagList,
        selectTag = viewModel::selectTag,
        search = { showSearchScreen = true },
        saveItem = viewModel::saveItem,
        showGalleryActivity = {
            val intent = Intent(activity.apply { slideIn() }, GalleryActivity::class.java)
            galleryActivityLauncher.launch(intent)
        },
        test = {
            test = true
        },
        onBack = finishActivity
    )

    if(showSearchScreen) {
        SearchScreen(
            searchTextState = viewModel.searchTextState,
            selectTagList = state.selectedTagList,
            tagList = state.searchTagList,
            select = viewModel::selectTag,
            onBack = { showSearchScreen = false },
        )
    }

    val qq = rememberTextFieldState()

    if(test) {
//        SearchBottomSheet(
//            state = qq,
//            deleteInput = {},
//            onDismissRequest = { test = false },
//        )
        TagSearchBottomSheet(
            searchTextState = viewModel.searchTextState,
            selectTagList = state.selectedTagList,
            tagList = state.searchTagList,
            select = viewModel::selectTag,
            onBack = { test = false },
        )
    }
}