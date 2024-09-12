package com.myStash.android.feature.item.feed

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraintsScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.myStash.android.common.resource.R
import com.myStash.android.common.util.isNotNull
import com.myStash.android.core.model.setCalender
import com.myStash.android.design_system.ui.DevicePreviews
import com.myStash.android.design_system.ui.color.ColorFamilyBlack20AndWhite
import com.myStash.android.design_system.ui.color.ColorFamilyGray200AndGray600
import com.myStash.android.design_system.ui.color.ColorFamilyLime100AndGray600
import com.myStash.android.design_system.ui.color.ColorFamilyLime700AndLime200
import com.myStash.android.design_system.ui.color.Gray350
import com.myStash.android.design_system.ui.color.Lime200
import com.myStash.android.design_system.ui.component.button.HasButton
import com.myStash.android.design_system.ui.component.calender.HasCalender
import com.myStash.android.design_system.ui.component.photo.SelectPhotoItem
import com.myStash.android.design_system.ui.component.photo.UnselectPhotoItem
import com.myStash.android.design_system.ui.component.text.HasFontWeight
import com.myStash.android.design_system.ui.component.text.HasText
import com.myStash.android.design_system.ui.theme.clickableNoRipple
import com.myStash.android.feature.item.component.AddItemAware
import com.myStash.android.feature.item.component.ItemTitleText

@Composable
fun AddFeedScreen(
    searchModalState: ModalBottomSheetState,
    state: AddFeedScreenState,
    showGalleryActivity: () -> Unit,
    onAction: (AddFeedScreenAction) -> Unit,
    showStyleSheet: () -> Unit,
    sheetContent: @Composable (ColumnScope.() -> Unit),
) {
    val scope = rememberCoroutineScope()

    var isShowDate by remember { mutableStateOf(false) }

    val dateFadeIn by animateFloatAsState(
        targetValue = if(isShowDate) 1f else 0f,
        animationSpec = tween(400),
        label = "header fade ani"
    )

    val dateMaxHeight by remember(state.calenderDate) {
        derivedStateOf {
            if(setCalender(state.calenderDate.year, state.calenderDate.monthValue).size > 42) {
                400.dp
            } else {
                360.dp
            }
        }
    }

    val dateHeight by animateDpAsState(
        targetValue = if(isShowDate) dateMaxHeight else 0.dp,
        animationSpec = tween(400),
        label = "date height"
    )

    val scrollState = rememberScrollState()
    
    val isComplete by remember(state) {
        derivedStateOf {
            state.selectedStyle.isNotNull() && state.selectedImageList.isNotEmpty()
        }
    }

    LaunchedEffect(state.calenderDate) {
        if(scrollState.value > 450) {
            scrollState.animateScrollBy(-(scrollState.value - 450f))
        }
    }
    

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
                LazyRow {
                    if (state.selectedImageList.size < 5) {
                        item {
                            UnselectPhotoItem(
                                cnt = state.selectedImageList.size,
                                onClick = showGalleryActivity
                            )
                        }
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
                        .border(
                            width = if (isShowDate) 1.dp else (-1).dp,
                            color = Lime200,
                            shape = RoundedCornerShape(size = 10.dp)
                        )
                        .background(
                            color = if (isShowDate) ColorFamilyLime100AndGray600 else ColorFamilyGray200AndGray600,
                            shape = RoundedCornerShape(size = 10.dp)
                        )
                        .clickable { isShowDate = !isShowDate },
                    contentAlignment = Alignment.Center
                ) {
                    HasText(
                        text = "${state.calenderDate.year}년 ${state.calenderDate.monthValue}월 ${state.calenderDate.dayOfMonth}일",
                        color = if(isShowDate) ColorFamilyLime700AndLime200 else ColorFamilyBlack20AndWhite,
                        fontWeight = HasFontWeight.Bold
                    )
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(dateHeight)
                        .alpha(dateFadeIn),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    HasCalender(
                        modifier = Modifier.width(360.dp),
                        year = state.calenderDate.year,
                        month = state.calenderDate.monthValue,
                        selectDate = state.calenderDate,
                        calenderDataList = setCalender(state.calenderDate.year, state.calenderDate.monthValue),
                        onPrevMonth = { onAction.invoke(AddFeedScreenAction.PrevMonth) },
                        onNextMonth = { onAction.invoke(AddFeedScreenAction.NextMonth) },
                        onClickDay = { onAction.invoke(AddFeedScreenAction.SelectDate(it)) },
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 32.dp, bottom = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ItemTitleText(
                        modifier = Modifier.weight(1f),
                        text = "Style"
                    )
                    if(state.selectedStyle != null) {
                        Row(
                            modifier = Modifier.clickableNoRipple { showStyleSheet.invoke() },
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                modifier = Modifier.size(14.dp),
                                painter = painterResource(id = R.drawable.img_edit),
                                contentDescription = "style edit",
                            )
                            HasText(
                                modifier = Modifier.padding(start = 2.dp),
                                text = "Edit",
                                color = Gray350,
                                fontSize = 13.dp
                            )
                        }
                    }
                }

                if(state.selectedStyle == null) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(44.dp)
                            .clip(shape = RoundedCornerShape(size = 10.dp))
                            .background(ColorFamilyGray200AndGray600)
                            .clickable { showStyleSheet.invoke() },
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.img_add_feed),
                            contentDescription = "style add",
                        )
                    }
                }

                state.selectedStyle?.let { style ->
                    LazyColumn(
                        modifier = Modifier.heightIn(max = 1000.dp),
                        userScrollEnabled = false
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
                        item { Box(modifier = Modifier.height(80.dp)) }
                    }
                } ?: run {
                    Box(modifier = Modifier.height(80.dp))
                }
            }
        }

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            Box(
                modifier = Modifier
                    .padding(top = 12.dp, bottom = 12.dp, start = 16.dp, end = 16.dp)
                    .shadow(
                        elevation = 10.dp,
                        spotColor = Color(0x14000000),
                        ambientColor = Color(0x14000000)
                    )
            ) {
                HasButton(
                    text = if(state.isEdit) "수정하기" else "등록하기",
                    isComplete = isComplete,
                    onClick = { onAction.invoke(AddFeedScreenAction.SaveFeed) }
                )
            }
        }
    }
}

@DevicePreviews
@Composable
fun AddFeedScreenPreview() {
    AddFeedScreen(
        searchModalState = ModalBottomSheetState(ModalBottomSheetValue.Expanded),
        state = AddFeedScreenState(),
        showGalleryActivity = {},
        showStyleSheet = {},
        onAction = {},
        sheetContent = {}
    )
}

fun Int.toDp(constraintsScope: BoxWithConstraintsScope): Dp {
    return with(constraintsScope) { this@toDp.dp }
}