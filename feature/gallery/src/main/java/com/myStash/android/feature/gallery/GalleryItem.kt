package com.myStash.android.feature.gallery

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.myStash.android.design_system.util.ShimmerLoadingAnimation


@Composable
fun GalleryItem(
    imageUri: Uri,
    selectedNumber: Int?,
    onClick: () -> Unit,
    zoom: () -> Unit
) {
    Box {
        SubcomposeAsyncImage(
            model = imageUri,
            contentDescription = "gallery image",
            modifier = Modifier
                .aspectRatio(1f)
                .clickable { zoom.invoke() },
            contentScale = ContentScale.Crop,
            loading = { ShimmerLoadingAnimation() },
            error = { ShimmerLoadingAnimation() }
        )

        Box(
            modifier = Modifier
                .size(40.dp)
                .background(if(selectedNumber == null) Color.White else Color.Red)
                .clickable { onClick.invoke() }
        ) {
            selectedNumber?.let {
                Text(selectedNumber.toString())
            }
        }
    }
}