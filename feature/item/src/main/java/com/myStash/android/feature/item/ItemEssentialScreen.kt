package com.myStash.android.feature.item

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text2.BasicTextField2
import androidx.compose.foundation.text2.input.TextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import com.myStash.android.core.model.Tag
import com.myStash.android.core.model.Type
import com.myStash.android.core.model.testManTypeTotalList
import com.myStash.android.core.model.testTagList
import com.myStash.android.design_system.ui.DevicePreviews
import com.myStash.android.design_system.ui.HasHeader
import com.myStash.android.design_system.ui.SearchText
import com.myStash.android.design_system.ui.tag.TagDeleteChipItem
import com.myStash.android.design_system.ui.type.TypeChipItem
import com.myStash.android.design_system.util.ShimmerLoadingAnimation

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ItemEssentialScreen(
    imageUri: String?,
    typeInputState: TextFieldState,
    tagInputState: TextFieldState,
    selectedType: Type?,
    typeTotalList: List<Type>,
    selectType: (Type) -> Unit,
    selectedTagList: List<Tag>,
    selectTag: (Tag) -> Unit,
    search: () -> Unit,
    saveItem: () -> Unit,
    showGalleryActivity: () -> Unit,
    test: () -> Unit = {},
    onBack: () -> Unit,
) {
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
            Text(text = "탭 선택 공간")
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(Color.White)
                .padding(horizontal = 12.dp)
                .padding(top = 24.dp)
        ) {
            ItemContentTitle(
                modifier = Modifier.padding(bottom = 16.dp),
                text = "사진"
            )
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .clickable { showGalleryActivity.invoke() }
            ) {
                SubcomposeAsyncImage(
                    model = imageUri,
                    contentDescription = "gallery image",
                    modifier = Modifier.aspectRatio(1f),
                    contentScale = ContentScale.Crop,
                    loading = { ShimmerLoadingAnimation() },
                    error = { ShimmerLoadingAnimation() }
                )
            }
            ItemContentTitle(
                modifier = Modifier.padding(top = 36.dp, bottom = 16.dp),
                text = "카테고리"
            )

            var expanded by remember { mutableStateOf(false) }

            ExposedDropdownMenuBox(
                modifier = Modifier
                    .border(
                        width = 1.dp,
                        color = Color(0xFF202020),
                        shape = RoundedCornerShape(size = 10.dp)
                    )
                    .fillMaxWidth()
                    .height(44.dp)
                    .background(color = Color(0xFFFFFFFF), shape = RoundedCornerShape(size = 10.dp)),
                expanded = expanded,
                onExpandedChange = { expanded = !expanded },
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable { expanded = true }
                ) {
                    BasicTextField2(state = typeInputState)
                }

                val scroll = rememberScrollState()

                ExposedDropdownMenu(
                    modifier = Modifier
                        .exposedDropdownSize()
                        .height(200.dp)
                        .verticalScroll(scroll),
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
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
                                Text(
                                    text = type.name,
                                    color = if(isSelected) Color(0xFF8A9918) else Color.Black
                                )
                            },
                            onClick = {
                                selectType.invoke(type)
                                expanded = false
                            },
                        )
                    }
                }
            }

            FlowRow(
                modifier = Modifier.fillMaxWidth()
            ) {
                typeTotalList.forEach { type ->
                    TypeChipItem(
                        name = type.name,
                        isSelected = type.name == selectedType?.name,
                        onClick = { selectType.invoke(type) }
                    )
                }
            }

            Row(
                modifier = Modifier.padding(top = 32.dp, bottom = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ItemContentTitle(
                    modifier = Modifier.padding(end = 5.dp),
                    text = "태그"
                )
                Text(
                    text = "(최대 30개)",
                    style = TextStyle(
                        fontSize = 12.sp,
                        lineHeight = 12.sp,
                        fontWeight = FontWeight(500),
                        color = Color(0xFF505050),
                    )
                )
            }

            SearchText(
                modifier = Modifier.fillMaxWidth(),
                textState = tagInputState,
                hint = "브랜드, 분위기, 계절, 컬러",
                onClick = search
            )
            Spacer(modifier = Modifier.height(20.dp))
            FlowRow(
                modifier = Modifier.fillMaxWidth()
            ) {
                selectedTagList.forEach { tag ->
                    TagDeleteChipItem(
                        name = tag.name,
                        onClick = { selectTag.invoke(tag) }
                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            ItemSelectRow {
                Row(
                    modifier = Modifier.padding(start = 12.dp, end = 8.dp).clickable { test.invoke() }
                ) {
                    Text(
                        text = "카테고리 선택",
                        style = TextStyle(
                            fontSize = 15.sp,
                            lineHeight = 15.sp,
                            fontWeight = FontWeight(500),
                            color = Color(0xFFA4A4A4),
                        )
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Box(modifier = Modifier.size(24.dp).background(Color.Black))
                }
            }
            Spacer(modifier = Modifier.weight(1f))
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
                    .height(48.dp)
                    .background(color = Color(0xFF202020), shape = RoundedCornerShape(size = 10.dp))
                    .clickable { saveItem.invoke() },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "등록하기",
                    style = TextStyle(
                        fontSize = 16.sp,
                        lineHeight = 16.sp,
                        fontWeight = FontWeight(700),
                        color = Color(0xFFE4F562),
                        textAlign = TextAlign.Center,
                    )
                )
            }
        }
    }
}

@DevicePreviews
@Composable
fun ItemEssentialScreenPreview() {
    ItemEssentialScreen(
        imageUri = null,
        typeInputState = TextFieldState(),
        tagInputState = TextFieldState(),
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