package com.myStash.android.feature.item

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text2.input.TextFieldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.myStash.android.core.model.Tag
import com.myStash.android.core.model.testTagList
import com.myStash.android.design_system.ui.DevicePreviews
import com.myStash.android.design_system.ui.HasHeader
import com.myStash.android.design_system.ui.SearchTextField
import com.myStash.android.design_system.ui.tag.TagDeleteChipItem
import com.myStash.android.design_system.ui.TagSelectChipItem
import com.myStash.android.feature.search.SearchResultItem

@Composable
fun TagSearchBottomSheet(
    searchTextState: TextFieldState,
    selectTagList: List<Tag>,
    tagList: List<Tag>,
    select: (Tag) -> Unit,
    onBack: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        HasHeader(
            text = "태그 등록",
            onBack = onBack
        )
        Spacer(modifier = Modifier.weight(1f))

        Column(
            modifier = Modifier
                .shadow(
                    elevation = 10.dp,
                    spotColor = Color.Black,
                    ambientColor = Color.Black,
                    shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
                )
                .clip(shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                .background(color = Color.White)
                .height(650.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SearchTextField(
                modifier = Modifier
                    .padding(top = 24.dp)
                    .padding(horizontal = 16.dp)
                    .border(
                        width = 1.dp,
                        color = Color(0xFF202020),
                        shape = RoundedCornerShape(size = 10.dp)
                    )
                    .fillMaxWidth()
                    .height(44.dp)
                    .background(color = Color(0xFFFFFFFF), shape = RoundedCornerShape(size = 10.dp)),
                textState = searchTextState
            )
            LazyRow(
                modifier = Modifier
                    .padding(top = 16.dp)
            ) {
                items(selectTagList) { tag ->
                    TagDeleteChipItem(
                        name = tag.name,
                        onClick = { select.invoke(tag) }
                    )
                }
            }
            Box(
                modifier = Modifier
                    .padding(top = 20.dp)
                    .fillMaxWidth()
                    .height(8.dp)
                    .background(color = Color(0xFFF1F1F1))
            )

            if(searchTextState.text.isEmpty()) {
                Text("사용한 태그")
                Spacer(modifier = Modifier.height(10.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(10.dp)
                        .background(Color.Gray)
                )
                Spacer(modifier = Modifier.height(10.dp))
                FlowRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    tagList.forEach { tag ->
                        val isSelected by remember(selectTagList) {
                            derivedStateOf {
                                selectTagList.contains(tag)
                            }
                        }

                        TagSelectChipItem(
                            name = tag.name,
                            isSelected = isSelected,
                            onClick = { select.invoke(tag) }
                        )
                    }
                }
            } else {
                Text("검색 결과")
                Spacer(modifier = Modifier.height(10.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(10.dp)
                        .background(Color.Gray)
                )
                Spacer(modifier = Modifier.height(10.dp))
                LazyColumn {
                    items(tagList) { tag ->
                        val isSelected by remember(selectTagList) {
                            derivedStateOf {
                                selectTagList.contains(tag)
                            }
                        }
                        SearchResultItem(
                            name = tag.name,
                            isSelected = isSelected,
                            onClick = { select.invoke(tag) }
                        )
                    }
                }
            }

        }

    }
    BackHandler(onBack = onBack)
}

@DevicePreviews
@Composable
fun TagSearchBottomSheetPreview() {
    TagSearchBottomSheet(
        searchTextState = TextFieldState(),
        selectTagList = emptyList(),
        tagList = testTagList,
        select = {},
        onBack = {}
    )
}