package com.myStash.android.feature.item.has

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.myStash.android.common.resource.R
import com.myStash.android.common.util.isNotNull
import com.myStash.android.common.util.isNotNullAndNotEmpty
import com.myStash.android.core.model.Type
import com.myStash.android.core.model.testManTypeTotalList
import com.myStash.android.core.model.testTagList
import com.myStash.android.design_system.ui.DevicePreviews
import com.myStash.android.design_system.ui.color.ColorFamilyBlack20AndWhite
import com.myStash.android.design_system.ui.color.ColorFamilyLime100AndGray550
import com.myStash.android.design_system.ui.color.ColorFamilyLime700AndLime300
import com.myStash.android.design_system.ui.color.ColorFamilyWhiteAndGray600
import com.myStash.android.design_system.ui.component.button.HasButton
import com.myStash.android.design_system.ui.component.content.ContentText
import com.myStash.android.design_system.ui.component.photo.SelectPhotoItem
import com.myStash.android.design_system.ui.component.photo.UnselectPhotoItem
import com.myStash.android.design_system.ui.component.tag.TagDeleteChipItem
import com.myStash.android.design_system.ui.component.text.HasFontWeight
import com.myStash.android.design_system.ui.component.text.HasText
import com.myStash.android.design_system.util.clickableRipple
import com.myStash.android.feature.item.component.AddItemAware
import com.myStash.android.feature.item.component.ItemTitleText
import kotlinx.coroutines.launch

@Composable
fun AddHasScreen(
    searchModalState: ModalBottomSheetState,
    state: AddHasScreenState,
    onAction: (AddHasScreenAction) -> Unit,
    sheetContent: @Composable (ColumnScope.() -> Unit),
) {
    val scope = rememberCoroutineScope()
    val dropDownScrollState = rememberScrollState()
    var dropDownExpanded by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()

    val headerFadeAni by animateFloatAsState(
        targetValue = if(searchModalState.targetValue == ModalBottomSheetValue.Expanded) 1f else 0f,
        animationSpec = tween(800),
        label = "header fade ani"
    )

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
                    .background(MaterialTheme.colors.background)
                    .padding(horizontal = 16.dp)
                    .verticalScroll(scrollState)
            ) {
                ItemTitleText(
                    modifier = Modifier.padding(top = 24.dp, bottom = 16.dp),
                    text = "사진"
                )
                state.imageUri?.let {
                    SelectPhotoItem(
                        imageUri = it,
                        onClick = { onAction.invoke(AddHasScreenAction.ShowGalleryActivity) }
                    )
                } ?: run {
                    UnselectPhotoItem(onClick = { onAction.invoke(AddHasScreenAction.ShowGalleryActivity) })
                }
                ItemTitleText(
                    modifier = Modifier.padding(top = 36.dp, bottom = 16.dp),
                    text = "카테고리"
                )
                ExposedDropdownMenuBox(
                    expanded = dropDownExpanded,
                    onExpandedChange = { dropDownExpanded = !dropDownExpanded },
                ) {
                    Column {
                        ContentText(
                            text = state.selectedType?.name ?: "카테고리 선택",
                            isTextFocus = state.selectedType != null,
                            isBorderFocus = dropDownExpanded,
                            onClick = {},
                            endContent = {
                                Image(
                                    modifier = Modifier.size(24.dp),
                                    painter = painterResource(id = if(dropDownExpanded) {
                                        if(isSystemInDarkTheme()) R.drawable.btn_drop_up_dark else R.drawable.btn_drop_up_light
                                    } else {
                                        if(isSystemInDarkTheme()) R.drawable.btn_drop_down_dark else R.drawable.btn_drop_down_light
                                    }),
                                    contentDescription = "drop down button"
                                )
                            }
                        )
                        Box(modifier = Modifier.padding(top = 7.dp))
                        ExposedDropdownMenu(
                            modifier = Modifier
                                .exposedDropdownSize()
                                .background(ColorFamilyWhiteAndGray600)
                                .height(220.dp)
                                .verticalScroll(dropDownScrollState),
                            expanded = dropDownExpanded,
                            onDismissRequest = { dropDownExpanded = false },
                        ) {
                            state.typeTotalList.forEach { type ->
                                val isSelected by remember {
                                    derivedStateOf {
                                        state.selectedType?.name == type.name
                                    }
                                }

                                DropdownMenuItem(
                                    modifier = Modifier
                                        .width(1000.dp)
                                        .background(if (isSelected) ColorFamilyLime100AndGray550 else ColorFamilyWhiteAndGray600),
                                    content = {
                                        HasText(
                                            text = type.name,
                                            color = if(isSelected) ColorFamilyLime700AndLime300 else ColorFamilyBlack20AndWhite,
                                            fontWeight = if(isSelected) HasFontWeight.Bold else HasFontWeight.Medium,
                                        )
                                    },
                                    onClick = {
                                        onAction.invoke(AddHasScreenAction.SelectType(type))
                                        dropDownExpanded = false
                                    },
                                )
                            }
                        }
                    }
                }

                Row(
                    modifier = Modifier.padding(top = 32.dp, bottom = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ItemTitleText(
                        modifier = Modifier.padding(end = 4.dp),
                        text = "태그"
                    )
                    HasText(
                        text = "(최대 30개)",
                        color = Color(0xFF505050),
                        fontSize = 12.dp,
                    )
                }
                ContentText(
                    text = "브랜드, 분위기, 계절, 컬러",
                    onClick = { onAction.invoke(AddHasScreenAction.ShowSearch) }
                )
                FlowRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp),
                ) {
                    state.selectedTagList.forEach { tag ->
                        TagDeleteChipItem(
                            name = tag.name,
                            onClick = { onAction.invoke(AddHasScreenAction.SelectTag(tag)) }
                        )
                    }
                }
                Box(modifier = Modifier.weight(1f))
                Box(modifier = Modifier.height(12.dp))
            }
            Box(
                modifier = Modifier
                    .background(MaterialTheme.colors.background)
                    .padding(bottom = 12.dp, start = 16.dp, end = 16.dp)
            ) {
                HasButton(
                    text = if(state.isEdit) "수정하기" else "등록하기",
                    isComplete = state.imageUri.isNotNullAndNotEmpty() && state.selectedType.isNotNull() && state.selectedTagList.isNotEmpty(),
                    onClick = { onAction.invoke(AddHasScreenAction.Save) }
                )
            }
        }
        if(headerFadeAni > 0) {
            Box(
                modifier = Modifier
                    .alpha(headerFadeAni)
                    .fillMaxWidth()
                    .height(90.dp)
                    .background(MaterialTheme.colors.surface)
                    .padding(start = 12.dp, top = 20.dp)
            ) {
                Image(
                    modifier = Modifier.clickableRipple { scope.launch { searchModalState.hide() } },
                    painter = painterResource(id = if(isSystemInDarkTheme())R.drawable.btn_finish_dark else R.drawable.btn_finish_light),
                    contentDescription = "header back"
                )
            }
        }
    }
}

@DevicePreviews
@Composable
fun ItemEssentialScreenPreview() {
    AddHasScreen(
        searchModalState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden),
        state = AddHasScreenState(
            typeTotalList = testManTypeTotalList,
            selectedType = Type(id = 1, name = "상의"),
            selectedTagList = testTagList,
        ),
        onAction = {},
        sheetContent = {}
    )
}