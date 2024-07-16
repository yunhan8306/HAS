package com.myStash.android.feature.item.style

import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text2.input.TextFieldState
import androidx.compose.foundation.text2.input.clearText
import androidx.compose.foundation.text2.input.rememberTextFieldState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.myStash.android.common.util.CommonActivityResultContract
import com.myStash.android.core.model.Has
import com.myStash.android.design_system.animation.slideIn
import com.myStash.android.design_system.ui.component.content.ContentText
import com.myStash.android.design_system.ui.component.content.ContentTextField
import com.myStash.android.design_system.ui.component.text.HasFontWeight
import com.myStash.android.design_system.ui.component.text.HasText
import com.myStash.android.design_system.ui.theme.clickableNoRipple
import com.myStash.android.feature.gallery.GalleryActivity
import com.myStash.android.feature.search.TagSearchBottomSheetLayout
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun AddStyleRoute(
    viewModel: AddStyleViewModel = hiltViewModel(),
    finishActivity: () -> Unit
) {

    val activity = LocalContext.current as ComponentActivity
    val galleryActivityLauncher = rememberLauncherForActivityResult(
        contract = CommonActivityResultContract(),
        onResult = { intent ->
//            intent?.getStringExtra("imageUri")?.let { viewModel.addImage(it) }
        }
    )

    val state by viewModel.collectAsState()
    val searchModalState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden, skipHalfExpanded = true)
    val scope = rememberCoroutineScope()

    viewModel.collectSideEffect { sideEffect ->
        when(sideEffect) {
            AddStyleSideEffect.Finish -> finishActivity.invoke()
            else -> Unit
        }
    }

    AddStyleScreen(
        imageUri = "",
        selectedHasList = listOf(Has(type = 0), Has(type = 0), Has(type = 1), Has(type = 0), Has(type = 0), Has(type = 1)),
        onUnSelectHas = {},


        selectedType = state.selectedType,
        typeTotalList = state.typeTotalList,
        selectType = viewModel::selectType,
        selectedTagList = state.selectedTagList,
        selectTag = viewModel::selectTag,
        search = {
            viewModel.deleteSearchText()
            scope.launch { searchModalState.show() }
        },
        saveItem = {},
        showGalleryActivity = {
            val intent = Intent(activity.apply { slideIn() }, GalleryActivity::class.java)
            galleryActivityLauncher.launch(intent)
        },
        onBack = finishActivity,
        searchModalState = searchModalState,
        sheetContent = {
            val configuration = LocalConfiguration.current
            val screenHeight = configuration.screenHeightDp.dp
            val sheetHeight = screenHeight - 120.dp

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(sheetHeight)
            ) {
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    item { Spacer(modifier = Modifier.width(8.dp)) }
                    items(state.typeTotalList) { type ->
                        Box(
                            modifier = Modifier
                                .padding(horizontal = 24.dp)
                                .clickableNoRipple {
                                    viewModel.selectType(type)
                                }
                        ) {
                            HasText(
                                text = type.name,
                                color = if(type == state.selectedType) Color(0xFF8A9918) else Color(0xFF202020),
                                fontWeight = if(type == state.selectedType) HasFontWeight.Bold else HasFontWeight.Medium
                            )
                        }
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(color = Color(0xFFF1F1F1))
                )
                val textFieldState = rememberTextFieldState()

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp, start = 16.dp, end = 16.dp)
                ) {
                    ContentTextField(
                        textState = textFieldState,
                        hint = "원하는 태그를 검색해 보세요",
                        delete = { textFieldState.clearText() }
                    )
                }




            }

//            TagSearchBottomSheetLayout(
//                searchModalState = searchModalState,
//                searchTextState = viewModel.searchTextState,
//                totalTagList = state.tagTotalList,
//                selectTagList = state.selectedTagList,
//                searchTagList = state.searchTagList,
//                buttonText = "완료",
//                onSelect = viewModel::selectTag,
//                onConfirm = { scope.launch { searchModalState.hide() } },
//                onDelete = viewModel::deleteSearchText,
//                onBack = { scope.launch { searchModalState.hide() }
//                }
//            )
        }
    )
}