package com.myStash.feature.gallery

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.myStash.core.model.Image
import org.orbitmvi.orbit.compose.collectAsState

@Composable
fun GalleryRoute(
    viewModel: GalleryViewModel = hiltViewModel(),
    imageList: List<Image>,
    complete: () -> Unit,
) {
    val state by viewModel.collectAsState()

    GalleryScreen(
        imageList = imageList,
        selectedImageList = state.selectedImageList,
        isSelected = viewModel::isSelected,
        complete = complete
    )
}