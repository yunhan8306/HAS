package com.myStash.feature.gallery

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.myStash.design_system.ui.theme.NoRippleTheme
import org.orbitmvi.orbit.compose.collectAsState

@Composable
fun GalleryScreen(
    viewModel: GalleryViewModel = hiltViewModel(),
) {

    val state by viewModel.collectAsState()

    val imageList by remember(state.imageList) {
        derivedStateOf { state.imageList }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(100.dp),
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

                LaunchedEffect(selectedNumber) {
                    Log.d("qwe123", "selectedNumber - $selectedNumber")
                }

                NoRippleTheme {
                    GalleryItem4(
                        imageUri = image.uri,
                        selectedNumber = selectedNumber,
                        onSelect = { viewModel.isSelected2(image) }
                    )
                }
            }
        }
    }
}