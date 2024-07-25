package com.myStash.android.feature.gallery

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.myStash.android.common.util.AppConfig
import com.myStash.android.core.model.Image
import com.myStash.android.design_system.ui.DevicePreviews
import com.myStash.android.design_system.ui.component.text.HasText

@Composable
fun GalleryScreen(
    imageList: List<Image>,
    selectedImageList: List<Image>,
    onEvent: (GalleryEvent) -> Unit,
    onComplete: () -> Unit,
    onRequestPermission: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Box(
            contentAlignment = Alignment.CenterEnd
        ) {
            Text(
                modifier = Modifier
                    .size(30.dp)
                    .background(Color.Yellow)
                    .clickable { onComplete.invoke() },
                text = "complete",
                textAlign = TextAlign.Center,
            )
        }
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            if(!AppConfig.allowReadMediaVisualUserSelected) {
                item {
                    Box(
                        modifier = Modifier
                            .aspectRatio(1f)
                            .background(Blue)
                            .clickable { onRequestPermission.invoke() },
                        contentAlignment = Alignment.Center
                    ) {
                        HasText(text = "test", color = White)
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
                    onClick = { onEvent.invoke(GalleryEvent.OnClick(image)) },
                    zoom = { onEvent.invoke(GalleryEvent.Zoom(image)) }
                )
            }
        }
    }
}

@DevicePreviews
@Composable
fun GalleryScreenPreview() {
    GalleryScreen(
        imageList = emptyList(),
        selectedImageList = emptyList(),
        onEvent = {},
        onComplete = {},
        onRequestPermission = {}
    )
}
