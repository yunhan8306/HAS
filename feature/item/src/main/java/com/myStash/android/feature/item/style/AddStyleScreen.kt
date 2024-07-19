package com.myStash.android.feature.item.style

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import coil.compose.SubcomposeAsyncImage
import com.myStash.android.common.resource.R
import com.myStash.android.core.model.Has
import com.myStash.android.core.model.Tag
import com.myStash.android.core.model.Type
import com.myStash.android.core.model.testManTypeTotalList
import com.myStash.android.core.model.testTagList
import com.myStash.android.design_system.ui.DevicePreviews
import com.myStash.android.design_system.ui.component.button.HasButton
import com.myStash.android.design_system.ui.component.content.ContentText
import com.myStash.android.design_system.ui.component.photo.SelectPhotoItem
import com.myStash.android.design_system.ui.component.photo.UnselectPhotoItem
import com.myStash.android.design_system.ui.component.tag.TagDeleteChipItem
import com.myStash.android.design_system.ui.component.text.HasFontWeight
import com.myStash.android.design_system.ui.component.text.HasText
import com.myStash.android.design_system.util.ShimmerLoadingAnimation
import com.myStash.android.design_system.util.ShimmerRectangleLoadingAnimation
import com.myStash.android.design_system.util.addFocusCleaner
import com.myStash.android.design_system.util.clickableRipple
import com.myStash.android.feature.item.component.ItemTitleText
import com.myStash.android.feature.item.component.AddItemAware
import kotlinx.coroutines.launch

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
            sheetBackgroundColor = Color.White,
            sheetElevation = 0.dp,
            backgroundColor = Color.Transparent,
            contentColor = Color.White
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
                                color = Color(0xFF505050),
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
                                    color = Color.White,
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
                        .background(Color.White)
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