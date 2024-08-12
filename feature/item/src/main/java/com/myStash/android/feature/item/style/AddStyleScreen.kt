package com.myStash.android.feature.item.style

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.myStash.android.common.resource.R
import com.myStash.android.core.model.Has
import com.myStash.android.design_system.ui.color.ColorFamilyGray500AndGray900
import com.myStash.android.design_system.ui.color.ColorFamilyWhiteAndGray800
import com.myStash.android.design_system.ui.color.White
import com.myStash.android.design_system.ui.component.button.HasButton
import com.myStash.android.design_system.ui.component.text.HasFontWeight
import com.myStash.android.design_system.ui.component.text.HasText
import com.myStash.android.design_system.util.ShimmerRectangleLoadingAnimation
import com.myStash.android.feature.item.component.AddItemAware

@Composable
fun AddStyleScreen(
    selectedHasList: List<Has>,
    saveItem: () -> Unit,
    sheetContent: @Composable (ColumnScope.() -> Unit),
) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val sheetPeekHeight = screenHeight - 390.dp

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
                if(selectedHasList.isEmpty()) {
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
                                painter = painterResource(id = R.drawable.img_big_hamong),
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
                        .height(286.dp)
                ) {
                    itemsIndexed(selectedHasList) { index, has ->
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
                                modifier = Modifier.padding(top = 12.dp, end = 12.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Image(
                                    modifier = Modifier.size(24.dp),
                                    painter = painterResource(id = R.drawable.btn_select_has),
                                    contentDescription = "has select"
                                )
                                HasText(
                                    text = (index + 1).toString(),
                                    color = White,
                                    fontSize = 12.dp,
                                    fontWeight = HasFontWeight.Bold
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
                modifier = Modifier.shadow(
                    elevation = 3.dp,
                    shape = RoundedCornerShape(size = 10.dp)
                ),
                text = "등록하기",
                isComplete = selectedHasList.isNotEmpty(),
                onClick = saveItem
            )
        }
    }
}