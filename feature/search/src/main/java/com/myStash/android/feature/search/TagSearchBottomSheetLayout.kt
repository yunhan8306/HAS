package com.myStash.android.feature.search

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text2.input.TextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.myStash.android.core.model.Tag
import com.myStash.android.core.model.testTagList
import com.myStash.android.design_system.ui.DevicePreviews
import com.myStash.android.design_system.ui.component.SpacerLineBox
import com.myStash.android.design_system.ui.component.button.HasButton
import com.myStash.android.design_system.ui.component.content.ContentTextField
import com.myStash.android.design_system.ui.component.tag.TagChipItem
import com.myStash.android.design_system.ui.component.tag.TagDeleteChipItem
import com.myStash.android.design_system.ui.component.tag.TagSearchItem
import com.myStash.android.design_system.ui.component.text.HasFontWeight
import com.myStash.android.design_system.ui.component.text.HasText

@Composable
fun TagSearchBottomSheetLayout(
    searchTextState: TextFieldState,
    totalTagList: List<Tag>,
    selectTagList: List<Tag>,
    searchTagList: List<Tag>,
    buttonText: String,
    onSelect: (Tag) -> Unit,
    onConfirm: () -> Unit,
    onDelete: () -> Unit,
    onBack: () -> Unit
) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val bottomSheetHeight = screenHeight - 50.dp

    Box(
        modifier = Modifier
            .fillMaxWidth()
//            .height(bottomSheetHeight)
            .padding(top = 32.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                ContentTextField(
                    textState = searchTextState,
                    hint = "원하는 태그를 검색해 보세요",
                    delete = onDelete
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(93.dp),
                    contentAlignment = Alignment.Center
                ) {
                    if(selectTagList.isNotEmpty()) {
                        Column(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            HasText(
                                text = "적용된 태그",
                                fontSize = 13.dp,
                                fontWeight = HasFontWeight.Bold
                            )
                            LazyRow(
                                modifier = Modifier.padding(top = 6.dp)
                            ) {
                                items(selectTagList) { tag ->
                                    TagDeleteChipItem(
                                        name = tag.name,
                                        onClick = { onSelect.invoke(tag) }
                                    )
                                }
                            }
                        }
                    } else {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            HasText(
                                text = "적용된 태그가 없습니다.",
                                fontSize = 14.dp,
                            )
                            HasText(
                                text = "원하는 태그를 검색하여 추가해 보세요.",
                                color = Color(0xFF909090),
                                fontSize = 12.dp
                            )
                        }
                    }
                }
            }

            SpacerLineBox()
            Column(
                modifier = Modifier
    //                .weight(1f)
                    .padding(top = 24.dp, start = 16.dp, end = 16.dp)
            ) {
                if(searchTextState.text.isEmpty()) {
                    val scrollState = rememberScrollState()

                    HasText(
                        modifier = Modifier.padding(bottom = 6.dp),
                        text = "사용한 태그",
                        fontSize = 13.dp,
                        fontWeight = HasFontWeight.Bold
                    )
                    FlowRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .verticalScroll(scrollState)
                    ) {
                        totalTagList.forEach { tag ->
                            val isSelected by remember(selectTagList) {
                                derivedStateOf {
                                    selectTagList.contains(tag)
                                }
                            }
                            TagChipItem(
                                name = tag.name,
                                isSelected = isSelected,
                                onClick = { onSelect.invoke(tag) }
                            )
                        }
                    }
                } else {
                    LazyColumn {
                        items(searchTagList) { tag ->
                            val isSelected by remember(selectTagList) {
                                derivedStateOf {
                                    selectTagList.contains(tag)
                                }
                            }
                            TagSearchItem(
                                name = tag.name,
                                searchText = searchTextState.text.toString(),
                                isSelected = isSelected,
                                onClick = { onSelect.invoke(tag) }
                            )
                        }
                    }
                }
            }
        }
        Column(
            modifier = Modifier.imePadding()
        ) {
            Column(
                modifier = Modifier.weight(1f).padding(start = 16.dp, end = 16.dp, bottom = 12.dp)
            ) {
                Spacer(modifier = Modifier.weight(1f))
                HasButton(
                    text = buttonText,
                    onClick = onConfirm
                )
            }
            BackHandler(onBack = onBack)
        }
    }
}

@DevicePreviews
@Composable
fun TagSearchBottomSheetLayoutPreview() {
    TagSearchBottomSheetLayout(
        searchTextState = TextFieldState("n"),
        totalTagList = testTagList,
        selectTagList = listOf(Tag(name = "고프코어"), Tag(name = "나이키")),
        searchTagList = listOf(Tag(name = "nike"), Tag(name = "needles"), Tag(name = "namacheko")),
        buttonText = "완료",
        onSelect = {},
        onConfirm = {},
        onDelete = {},
        onBack = {}
    )
}