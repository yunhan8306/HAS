package com.myStash.android.feature.gallery

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.myStash.android.common.util.AppConfig
import com.myStash.android.core.model.Image
import com.myStash.android.design_system.ui.component.text.HasText

@Composable
fun GalleryModalSheet(
    imageList: List<Image>,
    selectedImageList: List<Image>,
    onAction: (GalleryAction) -> Unit,
    onRequestPermission: () -> Unit,
) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val sheetPeekHeight = screenHeight - 76.dp

    Column(
        modifier = Modifier
            .background(Color.Black)
            .fillMaxWidth()
            .height(sheetPeekHeight)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp),
            contentAlignment = Alignment.CenterEnd
        ) {
            Row {
                HasText(
                    text = "(",
                    color = Color.White,
                    fontSize = 14.dp
                )
                HasText(
                    text = selectedImageList.size.toString(),
                    color = Color(0xFFE4F562),
                )
                HasText(
                    text = "/",
                    color = Color.White,
                    fontSize = 14.dp
                )
                HasText(
                    text = selectedImageList.size.toString(),
                    color = Color.White,
                )
                HasText(
                    text = ")",
                    color = Color.White,
                    fontSize = 14.dp
                )
            }
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
            horizontalArrangement = Arrangement.spacedBy(2.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            if(!AppConfig.allowReadMediaVisualUserSelected) {
                item {
                    Box(
                        modifier = Modifier
                            .aspectRatio(1f)
                            .background(Color.Blue)
                            .clickable { onRequestPermission.invoke() },
                        contentAlignment = Alignment.Center
                    ) {
                        HasText(text = "test", color = Color.White)
                    }
                }
            }
            items(
                items = imageList,
                key = { image -> image.uri }
            ) { image ->

                val selectedNumber: Int? by remember(selectedImageList) {
                    derivedStateOf {
                        val index = selectedImageList.indexOfFirst { it.uri == image.uri }
                        if(index != -1) index + 1 else null
                    }
                }

                GalleryItem(
                    imageUri = image.uri,
                    selectedNumber = selectedNumber,
                    onClick = { onAction.invoke(GalleryAction.AddSelectedList(image)) },
                    zoom = { onAction.invoke(GalleryAction.Focus(image)) }
                )
            }
        }
    }
}