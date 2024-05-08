package com.myStash.feature.gallery

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.myStash.core.model.Image

@Composable
fun GalleryItem(
    image: Image,
    selectedList: List<Image>,
    onSelect: (Image) -> Unit
) {
    var isSelected by remember { mutableStateOf(false) }

    LaunchedEffect(selectedList) {
        isSelected = selectedList.firstOrNull { it.name == image.name }?.let { true } ?: false
    }

    Box {
        AsyncImage(
            modifier = Modifier
                .aspectRatio(1f)
                .clickable { onSelect.invoke(image) },
            model = image.uri,
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Box(
            modifier = Modifier
                .size(30.dp)
                .background(if (isSelected) Color.Blue else Color.Yellow)
        )
    }
}