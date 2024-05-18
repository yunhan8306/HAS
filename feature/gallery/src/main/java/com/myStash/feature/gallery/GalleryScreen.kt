package com.myStash.feature.gallery

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import org.orbitmvi.orbit.compose.collectAsState

@Composable
fun GalleryScreen(
    viewModel: GalleryViewModel = hiltViewModel(),
    complete: () -> Unit,
) {

    val state by viewModel.collectAsState()

    val imageList by remember(state.imageList) {
        derivedStateOf { state.imageList }
    }

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
                    .clickable { complete.invoke() },
                text = "complete",
                textAlign = TextAlign.Center,
            )
        }
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(
                items = imageList,
                key = { it.uri }
            ) { image ->

                val selectedNumber: Int? by remember(state.selectedImageList) {
                    derivedStateOf {
                        val index = state.selectedImageList.indexOfFirst { it.uri == image.uri }
                        if(index != -1) index + 1 else null
                    }
                }

                GalleryItem(
                    imageUri = image.uri,
                    selectedNumber = selectedNumber,
                    onSelect = { viewModel.isSelected(image) }
                )
            }
        }
    }
}