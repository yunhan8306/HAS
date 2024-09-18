package com.myStash.android.feature.search.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text2.input.TextFieldState
import androidx.compose.foundation.text2.input.setTextAndPlaceCursorAtEnd
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import com.myStash.android.core.model.testTagList
import com.myStash.android.design_system.ui.DevicePreviews
import com.myStash.android.design_system.ui.color.Gray350
import com.myStash.android.design_system.ui.component.SpacerLineBox
import com.myStash.android.design_system.ui.component.button.HasButton
import com.myStash.android.design_system.ui.component.content.ContentTextField
import com.myStash.android.design_system.ui.component.tag.TagChipItem
import com.myStash.android.design_system.ui.component.tag.TagDeleteChipItem
import com.myStash.android.design_system.ui.component.tag.TagSearchItem
import com.myStash.android.design_system.ui.component.text.HasFontWeight
import com.myStash.android.design_system.ui.component.text.HasText
import com.myStash.android.design_system.ui.theme.HasSearchTheme
import com.myStash.android.design_system.ui.theme.clickableNoRipple
import com.myStash.android.design_system.util.addFocusCleaner
import com.myStash.android.design_system.util.rememberImeState

@Composable
fun SearchScreen(
    searchTextState: TextFieldState,
    state: SearchScreenState,
    onAction: (SearchAction) -> Unit,
) {
    val focusManager = LocalFocusManager.current
    val imeState = rememberImeState()

    LaunchedEffect(imeState.value) {
        if(!imeState.value) focusManager.clearFocus()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .clickableNoRipple {}
            .addFocusCleaner(focusManager),
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 12.dp)
        ) {
            ContentTextField(
                textState = searchTextState,
                hint = "원하는 태그를 검색해 보세요",
                isBack = true,
                back = { onAction.invoke(SearchAction.Finish) },
                delete = { onAction.invoke(SearchAction.DeleteText) },
                onDone = { searchTextState.setTextAndPlaceCursorAtEnd("${searchTextState.text} ") }
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(93.dp),
                contentAlignment = Alignment.Center
            ) {
                if(state.selectedTagList.isNotEmpty()) {
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
                            items(
                                items = state.selectedTagList,
                                key = { it.id!! }
                            ) { tag ->
                                TagDeleteChipItem(
                                    name = tag.name,
                                    onClick = { onAction.invoke(SearchAction.SelectTag(tag)) }
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
                            color = Gray350,
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
                        state.totalTagList.forEach { tag ->
                            val isSelected by remember(state.selectedTagList) {
                                derivedStateOf {
                                    tag.checkSelected(state.selectedTagList)
                                }
                            }
                            TagChipItem(
                                name = tag.name,
                                isSelected = isSelected,
                                onClick = { onAction.invoke(SearchAction.SelectTag(tag)) }
                            )
                        }
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.weight(1f)
                    ) {
                        items(
                            items = state.searchTagList,
                            key = { it.id!! }
                        ) { tag ->
                            val isSelected by remember(state.selectedTagList) {
                                derivedStateOf {
                                    tag.checkSelected(state.selectedTagList)
                                }
                            }
                            TagSearchItem(
                                name = tag.name,
                                cnt = tag.usedCnt.toString(),
                                searchText = searchTextState.text.toString(),
                                isSelected = isSelected,
                                onClick = { onAction.invoke(SearchAction.SelectTag(tag)) }
                            )
                        }
                    }
                }
            }
        }
    }
    state.selectedCntText?.let { text ->
        Column(
            modifier = Modifier
                .imePadding()
                .padding(start = 16.dp, end = 16.dp, bottom = 12.dp)
        ) {
            Spacer(modifier = Modifier.weight(1f))
            HasButton(
                text = text,
                onClick = { onAction.invoke(SearchAction.Confirm) }
            )
        }
    }
}

@DevicePreviews
@Composable
fun SearchScreenPreview() {
    HasSearchTheme {
        SearchScreen(
            searchTextState = TextFieldState(),
            state = SearchScreenState(
                totalTagList = testTagList,
                selectedTagList = emptyList(),
                searchTagList = emptyList(),
                selectedCntText = "5",
            ),
            onAction = {}
        )
    }
}