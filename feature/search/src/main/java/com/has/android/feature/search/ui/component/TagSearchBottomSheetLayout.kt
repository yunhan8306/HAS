package com.has.android.feature.search.ui.component

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text2.input.TextFieldState
import androidx.compose.foundation.text2.input.setTextAndPlaceCursorAtEnd
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import com.has.android.core.model.Tag
import com.has.android.core.model.testTagList
import com.has.android.design_system.ui.DevicePreviews
import com.has.android.design_system.ui.component.SpacerLineBox
import com.has.android.design_system.ui.component.button.HasButton
import com.has.android.design_system.ui.component.content.ContentTextField
import com.has.android.design_system.ui.component.tag.TagChipItem
import com.has.android.design_system.ui.component.tag.TagDeleteChipItem
import com.has.android.design_system.ui.component.tag.TagSearchItem
import com.has.android.design_system.ui.component.text.HasFontWeight
import com.has.android.design_system.ui.component.text.HasText
import com.has.android.design_system.util.addFocusCleaner
import com.has.android.design_system.util.rememberImeState

@Composable
fun TagSearchBottomSheetLayout(
    searchModalState: ModalBottomSheetState,
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
    val focusManager = LocalFocusManager.current
    val imeState = rememberImeState()

    LaunchedEffect(imeState.value) {
        if(!imeState.value) focusManager.clearFocus()
    }

    LaunchedEffect(searchModalState.targetValue) {
        if(searchModalState.targetValue == ModalBottomSheetValue.Hidden) focusManager.clearFocus()
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .addFocusCleaner(focusManager)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clickable { onBack.invoke() }
            )
            Column(
                modifier = Modifier
                    .shadow(elevation = 10.dp, shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                    .clip(shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                    .background(MaterialTheme.colors.background)
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(top = 32.dp)
                ) {
                    ContentTextField(
                        textState = searchTextState,
                        hint = "원하는 태그를 검색해 보세요",
                        delete = onDelete,
                        onDone = { searchTextState.setTextAndPlaceCursorAtEnd("${searchTextState.text} ") }
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
                Box {
                    Column(
                        modifier = Modifier.padding(top = 24.dp, start = 16.dp, end = 16.dp)
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
                                            tag.checkSelected(selectTagList)
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
                            LazyColumn(
                                modifier = Modifier.weight(1f)
                            ) {
                                items(searchTagList) { tag ->
                                    val isSelected by remember(selectTagList) {
                                        derivedStateOf {
                                            tag.checkSelected(selectTagList)
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
            }
        }
        Column(
            modifier = Modifier
                .imePadding()
                .padding(start = 16.dp, end = 16.dp, bottom = 12.dp)
        ) {
            Spacer(modifier = Modifier.weight(1f))
            HasButton(
                text = buttonText,
                onClick = onConfirm
            )
        }
        if(searchModalState.isVisible || searchModalState.targetValue == ModalBottomSheetValue.Expanded) {
            BackHandler(onBack = onBack)
        }
    }
}

@DevicePreviews
@Composable
fun TagSearchBottomSheetLayoutPreview() {
    TagSearchBottomSheetLayout(
        searchModalState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Expanded),
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