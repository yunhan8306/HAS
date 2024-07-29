package com.myStash.android.feature.item.feed

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text2.input.TextFieldState
import androidx.compose.foundation.text2.input.clearText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import com.myStash.android.common.util.isNotNull
import com.myStash.android.core.model.StyleScreenModel
import com.myStash.android.core.model.Tag
import com.myStash.android.core.model.selectOrNull
import com.myStash.android.design_system.ui.component.SpacerLineBox
import com.myStash.android.design_system.ui.component.button.HasButton
import com.myStash.android.design_system.ui.component.button.HasSelectButton
import com.myStash.android.design_system.ui.component.content.ContentTextField
import com.myStash.android.design_system.ui.component.style.StyleMainItem
import com.myStash.android.design_system.ui.component.tag.TagChipItem
import com.myStash.android.design_system.ui.component.tag.TagDeleteChipItem
import com.myStash.android.design_system.ui.component.tag.TagSearchItem
import com.myStash.android.design_system.ui.component.text.HasFontWeight
import com.myStash.android.design_system.ui.component.text.HasText
import com.myStash.android.design_system.ui.theme.clickableNoRipple
import com.myStash.android.design_system.util.addFocusCleaner
import com.myStash.android.design_system.util.rememberImeState

@Composable
fun SelectStyleModalSheet(
    selectStyleModalState: ModalBottomSheetState,
    state: AddFeedScreenState,
    searchTextState: TextFieldState,

    totalTagList: List<Tag>,
    selectTagList: List<Tag>,
    searchTagList: List<Tag>,
    buttonText: String,
    onSelect: (Tag) -> Unit,
    onConfirm: () -> Unit,
    onDelete: () -> Unit,
    onBack: () -> Unit,

    onAction: (AddFeedScreenAction) -> Unit = {}
) {
    val focusManager = LocalFocusManager.current
    val imeState = rememberImeState()

    var modalType by remember { mutableStateOf(SelectStyleModalType.STYLE) }

    var selectedStyle by remember { mutableStateOf<StyleScreenModel?>(null) }

    LaunchedEffect(imeState.value) {
        if(!imeState.value) {
            focusManager.clearFocus()
        } else {
            modalType = SelectStyleModalType.TAG
        }
    }

    LaunchedEffect(selectStyleModalState.targetValue) {
        if(selectStyleModalState.targetValue == ModalBottomSheetValue.Hidden) focusManager.clearFocus()
    }

    LaunchedEffect(Unit) {
        modalType = SelectStyleModalType.STYLE
        selectedStyle = state.selectedStyle
    }

    LaunchedEffect(modalType) {
        if(modalType == SelectStyleModalType.STYLE) focusManager.clearFocus()
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
                    .shadow(
                        elevation = 10.dp,
                        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
                    )
                    .background(
                        Color.White,
                        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
                    )
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
                when(modalType) {
                    SelectStyleModalType.TAG -> {
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
                    SelectStyleModalType.STYLE -> {
                        LazyVerticalGrid(
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .weight(1f)
                                .fillMaxWidth(),
                            columns = GridCells.Fixed(2),
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(
                                count = 2,
                                key = { it }
                            ) { index ->
                                Box(
                                    modifier = Modifier
                                        .height(44.dp)
                                        .padding(top = 24.dp, end = 4.dp),
                                    contentAlignment = Alignment.TopEnd
                                ) {
                                    if(index == 1) HasText(text = "총 ${state.styleList.size}개")
                                }
                            }

                            items(
                                items = state.styleList,
                                key = { it.id }
                            ) { style ->
                                val isSelected by remember(selectedStyle) {
                                    derivedStateOf {
                                        selectedStyle?.id == style.id
                                    }
                                }

                                StyleMainItem(
                                    style = style,
                                    isSelected = isSelected,
                                    onClick = { selectedStyle = style.selectOrNull(selectedStyle) },
                                    onLongClick = {  }
                                )
                            }
                        }
                    }
                }
            }
        }

        when(modalType) {
            SelectStyleModalType.TAG -> {
                Column(
                    modifier = Modifier
                        .imePadding()
                        .padding(start = 16.dp, end = 16.dp, bottom = 12.dp)
                ) {
                    Spacer(modifier = Modifier.weight(1f))
                    HasButton(
                        text = "${state.styleList.size}개 Style 보기",
                        isComplete = state.styleList.isNotEmpty(),
                        onClick = { modalType = SelectStyleModalType.STYLE }
                    )
                }
            }
            SelectStyleModalType.STYLE -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    MainStyleBottomModel(
                        selectedStyle = selectedStyle,
                        onSelect = { onAction.invoke(AddFeedScreenAction.SelectStyle(selectedStyle)) },
                        onCancel = { selectedStyle = null },
                    )
                }
            }
        }

        if(selectStyleModalState.isVisible || selectStyleModalState.targetValue == ModalBottomSheetValue.Expanded) {
            BackHandler {
                when {
                    modalType == SelectStyleModalType.TAG -> {
                        modalType = SelectStyleModalType.STYLE
                        searchTextState.clearText()
                    }
                    selectedStyle.isNotNull() -> selectedStyle = null
                    else -> onBack.invoke()
                }
            }
        }
    }
}

enum class SelectStyleModalType {
    TAG, STYLE
}

@Composable
fun MainStyleBottomModel(
    selectedStyle: StyleScreenModel?,
    onSelect: () -> Unit,
    onCancel: () -> Unit,
) {

    var isShow by remember { mutableStateOf(false) }

    LaunchedEffect(selectedStyle) {
        isShow = selectedStyle.isNotNull()
    }

    if(isShow) {
        Column(
            modifier = Modifier
                .shadow(
                    elevation = 10.dp,
                    spotColor = Color(0x29000000),
                    ambientColor = Color(0x29000000)
                )
                .fillMaxWidth()
                .height(84.dp)
                .background(
                    color = Color(0xFFFFFFFF),
                    shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                )
                .clickableNoRipple { }
                .padding(16.dp)
        ) {
            HasSelectButton(
                modifier = Modifier.padding(top = 16.dp),
                selectText = "피드 등록하기",
                onSelect = onSelect,
                onCancel = onCancel
            )
        }
    }
}