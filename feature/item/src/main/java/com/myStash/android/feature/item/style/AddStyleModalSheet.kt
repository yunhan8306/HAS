package com.myStash.android.feature.item.style

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text2.input.TextFieldState
import androidx.compose.foundation.text2.input.clearText
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.myStash.android.common.resource.R
import com.myStash.android.design_system.ui.color.ColorFamilyBlack20AndWhite
import com.myStash.android.design_system.ui.color.ColorFamilyGray200AndGray600
import com.myStash.android.design_system.ui.color.ColorFamilyGray300AndGray400
import com.myStash.android.design_system.ui.color.ColorFamilyLime700AndLime300
import com.myStash.android.design_system.ui.color.Purple
import com.myStash.android.design_system.ui.color.White
import com.myStash.android.design_system.ui.component.SpacerBox
import com.myStash.android.design_system.ui.component.content.ContentTextField
import com.myStash.android.design_system.ui.component.text.HasFontWeight
import com.myStash.android.design_system.ui.component.text.HasText
import com.myStash.android.design_system.ui.theme.clickableNoRipple
import com.myStash.android.design_system.util.ShimmerLoadingAnimation
import com.myStash.android.design_system.util.addFocusCleaner
import com.myStash.android.design_system.util.rememberImeState

@Composable
fun AddStyleModalSheet(
    searchModalState: ModalBottomSheetState,
    searchTextState: TextFieldState,
    state: AddStyleScreenState,
    onAction: (AddStyleScreenAction) -> Unit
) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val sheetHeight = screenHeight - 120.dp

    val focusManager = LocalFocusManager.current
    val imeState = rememberImeState()

    LaunchedEffect(imeState.value) {
        if(!imeState.value) {
            focusManager.clearFocus()
            searchModalState.show()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(sheetHeight)
            .addFocusCleaner(focusManager)
    ) {
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            item { Spacer(modifier = Modifier.width(8.dp)) }
            items(state.typeTotalList) { type ->
                Box(
                    modifier = Modifier
                        .padding(horizontal = 24.dp)
                        .clickableNoRipple { onAction.invoke(AddStyleScreenAction.SelectType(type)) }
                ) {
                    HasText(
                        text = type.name,
                        color = if(type.id == state.selectedType.id) ColorFamilyLime700AndLime300 else ColorFamilyBlack20AndWhite,
                        fontWeight = if(type.id == state.selectedType.id) HasFontWeight.Bold else HasFontWeight.Medium
                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(color = ColorFamilyGray200AndGray600)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp, start = 16.dp, end = 16.dp)
        ) {
            ContentTextField(
                textState = searchTextState,
                hint = "원하는 태그를 검색해 보세요",
                delete = { searchTextState.clearText() }
            )

            LazyVerticalGrid(
                modifier = Modifier
                    .padding(top = 12.dp)
                    .weight(1f)
                    .fillMaxWidth(),
                columns = GridCells.Fixed(3),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(
                    items = state.hasList,
                    key = { it.id ?: 0 }
                ) { has ->
                    val isSelected by remember(state.selectedHasList) {
                        derivedStateOf {
                            has.checkSelected(state.selectedHasList)
                        }
                    }

                    val selectedNumber by remember(state.selectedHasList) {
                        derivedStateOf {
                            state.selectedHasList.indexOf(has) + 1
                        }
                    }

                    Box(
                        modifier = if(isSelected) {
                            Modifier.border(width = 2.dp, color = Purple, shape = RoundedCornerShape(size = 8.dp))
                        } else {
                            Modifier.border(width = 1.dp, color = ColorFamilyGray300AndGray400, shape = RoundedCornerShape(size = 8.dp))
                        }
                    ) {
                        SubcomposeAsyncImage(
                            model = has.imagePath,
                            contentDescription = "gallery image",
                            modifier = Modifier
                                .aspectRatio(1f)
                                .clip(shape = RoundedCornerShape(size = 8.dp))
                                .clickable { onAction.invoke(AddStyleScreenAction.SelectHas(has)) },
                            contentScale = ContentScale.Crop,
                            loading = { ShimmerLoadingAnimation() },
                            error = { ShimmerLoadingAnimation() }
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(8.dp),
                            contentAlignment = Alignment.TopEnd
                        ) {
                            Box(
                                modifier = Modifier.size(18.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                if(isSelected) {
                                    Image(
                                        painter = painterResource(id = R.drawable.btn_purple_select),
                                        contentDescription = "has select"
                                    )
                                    HasText(
                                        text = selectedNumber.toString(),
                                        color = White,
                                        fontSize = 12.dp,
                                        fontWeight = HasFontWeight.Bold
                                    )
                                } else {
                                    Image(
                                        painter = painterResource(id = R.drawable.btn_purple_no_select),
                                        contentDescription = "has select"
                                    )
                                }
                            }
                        }
                    }
                }
                items(3) { SpacerBox(52.dp) }
            }
        }
    }
}