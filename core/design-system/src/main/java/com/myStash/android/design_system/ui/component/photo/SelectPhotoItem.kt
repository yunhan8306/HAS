package com.myStash.android.design_system.ui.component.photo

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.myStash.android.design_system.util.ShimmerLoadingAnimation

@Composable
fun SelectPhotoItem(
    imageUri: String?,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(100.dp)
            .clip(RoundedCornerShape(10.dp))
            .clickable { onClick.invoke() }
    ) {
        SubcomposeAsyncImage(
            model = imageUri,
            contentDescription = "select photo",
            modifier = Modifier.aspectRatio(1f),
            contentScale = ContentScale.Crop,
            loading = { ShimmerLoadingAnimation() },
            error = { ShimmerLoadingAnimation() }
        )
    }
}