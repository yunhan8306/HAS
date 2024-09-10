package com.myStash.android.feature.item.style

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.myStash.android.common.resource.R
import com.myStash.android.core.model.getType
import com.myStash.android.design_system.ui.color.ColorFamilyGray500AndGray900
import com.myStash.android.design_system.ui.color.ColorFamilyWhiteAndGray800
import com.myStash.android.design_system.ui.component.button.HasButton
import com.myStash.android.design_system.ui.component.text.HasText
import com.myStash.android.design_system.ui.component.type.TypeSquareChipItem
import com.myStash.android.design_system.util.ShimmerRectangleLoadingAnimation
import com.myStash.android.feature.item.component.AddItemAware

@Composable
fun AddStyleScreen(
    state: AddStyleScreenState,
    onAction: (AddStyleScreenAction) -> Unit,
    sheetContent: @Composable (ColumnScope.() -> Unit),
) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val sheetPeekHeight = screenHeight - 390.dp
    val selectedHasHorizontalScrollState = rememberLazyListState()

    LaunchedEffect(state.selectedHasList) {
        if(state.selectedHasList.isNotEmpty()) {
            selectedHasHorizontalScrollState.animateScrollToItem(state.selectedHasList.size - 1)
        }
    }

    AddItemAware {
        BottomSheetScaffold(
            sheetPeekHeight = sheetPeekHeight,
            sheetContent = sheetContent,
            sheetBackgroundColor = ColorFamilyWhiteAndGray800,
            sheetElevation = 0.dp,
            backgroundColor = MaterialTheme.colors.background,
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                if(state.selectedHasList.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                modifier = Modifier.size(150.dp),
                                painter = painterResource(id = if(isSystemInDarkTheme()) R.drawable.img_hadung_empty_two_dark else R.drawable.img_hadung_empty_two_light),
                                contentDescription = "has select"
                            )
                            HasText(
                                modifier = Modifier.padding(top = 16.dp),
                                text = "원하는 코디를 완성해 보세요.",
                                color = ColorFamilyGray500AndGray900,
                            )
                        }
                    }
                }
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(286.dp),
                    state = selectedHasHorizontalScrollState
                ) {
                    itemsIndexed(state.selectedHasList) { index, has ->
                        Box(
                            contentAlignment = Alignment.TopEnd
                        ) {
                            SubcomposeAsyncImage(
                                model = has.imagePath,
                                contentDescription = "has image",
                                modifier = Modifier.aspectRatio(220 / 286f),
                                contentScale = ContentScale.Crop,
                                loading = { ShimmerRectangleLoadingAnimation() },
                                error = { ShimmerRectangleLoadingAnimation() }
                            )
                            Box(
                                modifier = Modifier.padding(top = 10.dp, end = 10.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                TypeSquareChipItem(
                                    name = has.type.getType(state.typeTotalList)?.name ?: "미선택",
                                    onClick = { onAction.invoke(AddStyleScreenAction.SelectHas(has)) }
                                )
                            }
                        }
                    }
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
            }
        }
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Box(
            modifier = Modifier.padding(bottom = 12.dp, start = 16.dp, end = 16.dp),
        ) {
            HasButton(
                modifier = Modifier.shadow(elevation = 3.dp, shape = RoundedCornerShape(size = 10.dp)),
                text = if(state.isEdit) "수정하기" else "등록하기",
                isComplete = state.selectedHasList.isNotEmpty(),
                onClick = { onAction.invoke(AddStyleScreenAction.SaveStyle) }
            )
        }
    }
}