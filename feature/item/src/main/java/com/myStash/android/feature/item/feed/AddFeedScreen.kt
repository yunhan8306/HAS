package com.myStash.android.feature.item.feed

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.myStash.android.common.resource.R
import com.myStash.android.design_system.ui.component.photo.SelectPhotoItem
import com.myStash.android.design_system.ui.component.photo.UnselectPhotoItem
import com.myStash.android.design_system.ui.component.text.HasFontWeight
import com.myStash.android.design_system.ui.component.text.HasText
import com.myStash.android.feature.item.component.AddItemAware
import com.myStash.android.feature.item.component.ItemTitleText

@Composable
fun AddFeedScreen(
    searchModalState: ModalBottomSheetState,
    state: AddFeedScreenState,
//    imageUri: String?,
//    selectedType: Type?,
//    typeTotalList: List<Type>,
//    selectType: (Type) -> Unit,
//    selectedTagList: List<Tag>,
//    selectTag: (Tag) -> Unit,
//    search: () -> Unit,
//    saveItem: () -> Unit,
    showGalleryActivity: () -> Unit,
//    onBack: () -> Unit,
    showStyleSheet: () -> Unit,
    sheetContent: @Composable (ColumnScope.() -> Unit),
) {
    val scope = rememberCoroutineScope()
    val headerFadeAni by animateFloatAsState(
        targetValue = if(searchModalState.targetValue == ModalBottomSheetValue.Expanded) 1f else 0f,
        animationSpec = tween(800),
        label = "header fade ani"
    )
    val scrollState = rememberScrollState()

    ModalBottomSheetLayout(
        sheetState = searchModalState,
        sheetContent = sheetContent,
        sheetBackgroundColor = Color.Transparent,
        scrimColor = Color.Transparent,
        sheetElevation = 0.dp,
    ) {
        AddItemAware {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(Color.White)
                    .padding(horizontal = 16.dp)
                    .verticalScroll(scrollState)
            ) {
                ItemTitleText(
                    modifier = Modifier.padding(top = 24.dp, bottom = 16.dp),
                    text = "사진"
                )
                LazyRow {
                    if (state.selectedImageList.size < 5) {
                        item { UnselectPhotoItem(onClick = showGalleryActivity) }
                    }
                    items(state.selectedImageList) { uri ->
                        Row {
                            SelectPhotoItem(
                                imageUri = uri,
                                onClick = showGalleryActivity
                            )
                        }
                    }
                }
                ItemTitleText(
                    modifier = Modifier.padding(top = 32.dp, bottom = 16.dp),
                    text = "날짜"
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp)
                        .background(
                            color = Color(0xFFF1F1F1),
                            shape = RoundedCornerShape(size = 10.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    HasText(
                        text = "2024년 5월 7일",
                        fontWeight = HasFontWeight.Bold
                    )
                }

                ItemTitleText(
                    modifier = Modifier.padding(top = 32.dp, bottom = 16.dp),
                    text = "Style"
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp)
                        .background(
                            color = Color(0xFFF1F1F1),
                            shape = RoundedCornerShape(size = 10.dp)
                        )
                        .clickable { showStyleSheet.invoke() },
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        modifier = Modifier.size(20.dp),
                        painter = painterResource(id = R.drawable.btn_style_add),
                        contentDescription = "style add",
                    )
                }

                state.selectedStyle?.let { style ->
                    LazyColumn(
                        modifier = Modifier.heightIn(max = 1000.dp)
                    ) {
                        items(
                            items = style.hasList
                        ) { has ->
                            AddStyleHasItem(
                                has = has,
                                typeTotalList = state.typeTotalList,
                                tagTotalList = state.tagTotalList
                            )
                        }
                    }
                }
            }
        }
    }
}