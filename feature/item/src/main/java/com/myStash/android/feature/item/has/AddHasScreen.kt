package com.myStash.android.feature.item.has

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.myStash.android.common.resource.R
import com.myStash.android.core.model.Tag
import com.myStash.android.core.model.Type
import com.myStash.android.core.model.testManTypeTotalList
import com.myStash.android.core.model.testTagList
import com.myStash.android.design_system.ui.DevicePreviews
import com.myStash.android.design_system.ui.component.button.HasButton
import com.myStash.android.design_system.ui.component.content.ContentText
import com.myStash.android.design_system.ui.component.header.HasHeader
import com.myStash.android.design_system.ui.component.photo.SelectPhotoItem
import com.myStash.android.design_system.ui.component.photo.UnselectPhotoItem
import com.myStash.android.design_system.ui.component.tag.TagDeleteChipItem
import com.myStash.android.design_system.ui.component.text.HasFontWeight
import com.myStash.android.design_system.ui.component.text.HasText
import com.myStash.android.feature.item.component.ItemTitleText

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AddHasScreen(
    imageUri: String?,
    selectedType: Type?,
    typeTotalList: List<Type>,
    selectType: (Type) -> Unit,
    selectedTagList: List<Tag>,
    selectTag: (Tag) -> Unit,
    search: () -> Unit,
    saveItem: () -> Unit,
    showGalleryActivity: () -> Unit,
    onBack: () -> Unit,
) {
    val dropDownScrollState = rememberScrollState()
    var dropDownExpanded by remember { mutableStateOf(false) }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        HasHeader(
            text = "등록하기",
            onBack = onBack
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .background(Color.Red),
            contentAlignment = Alignment.Center
        ) {
            HasText(text = "탭 선택 공간")
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(Color.White)
                .padding(horizontal = 12.dp)
                .padding(top = 24.dp)
        ) {
            ItemTitleText(
                modifier = Modifier.padding(bottom = 16.dp),
                text = "사진"
            )
            imageUri?.let {
                SelectPhotoItem(
                    imageUri = imageUri,
                    onClick = showGalleryActivity
                )
            } ?: run {
                UnselectPhotoItem(onClick = showGalleryActivity)
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
                        text = selectedType?.name ?: "카테고리 선택",
                        textColor = if(selectedType != null) Color(0xFF202020) else Color(0xFFA4A4A4),
                        borderColor = if(dropDownExpanded) Color(0xFF202020) else Color(0xFFE1E1E1),
                        onClick = {},
                        endContent = {
                            Image(
                                modifier = Modifier.size(24.dp),
                                painter = painterResource(id = R.drawable.btn_down),
                                contentDescription = "btn down"
                            )
                        }
                    )
                    Box(modifier = Modifier.padding(top = 7.dp))
                    ExposedDropdownMenu(
                        modifier = Modifier
                            .exposedDropdownSize()
                            .height(220.dp)
                            .verticalScroll(dropDownScrollState),
                        expanded = dropDownExpanded,
                        onDismissRequest = { dropDownExpanded = false },
                    ) {
                        typeTotalList.forEach { type ->
                            val isSelected by remember {
                                derivedStateOf {
                                    selectedType?.name == type.name
                                }
                            }

                            DropdownMenuItem(
                                modifier = Modifier
                                    .width(1000.dp)
                                    .background(if (isSelected) Color(0xFFFCFFE7) else Color.White),
                                content = {
                                    HasText(
                                        text = type.name,
                                        color = if(isSelected) Color(0xFF8A9918) else Color(0xFF202020),
                                        fontWeight = if(isSelected) HasFontWeight.Bold else HasFontWeight.Medium,
                                    )
                                },
                                onClick = {
                                    selectType.invoke(type)
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
                onClick = search
            )
            FlowRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
            ) {
                selectedTagList.forEach { tag ->
                    TagDeleteChipItem(
                        name = tag.name,
                        onClick = { selectTag.invoke(tag) }
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1f))
        }
        Box(
            modifier = Modifier.padding(bottom = 12.dp, start = 16.dp, end = 16.dp)
        ) {
            HasButton(
                text = "등록하기",
                isComplete = true,
                onClick = saveItem
            )
        }
    }
}

@DevicePreviews
@Composable
fun ItemEssentialScreenPreview() {
    AddHasScreen(
        imageUri = null,
        selectedType = Type(id = 1, name = "상의"),
        typeTotalList = testManTypeTotalList,
        selectType = {},
        selectedTagList = testTagList,
        selectTag = {},
        search = {},
        showGalleryActivity = {},
        saveItem = {},
        onBack = {}
    )
}