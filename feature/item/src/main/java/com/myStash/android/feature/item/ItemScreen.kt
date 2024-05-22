package com.myStash.android.feature.item

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text2.input.TextFieldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.myStash.android.design_system.ui.DevicePreviews
import com.myStash.android.design_system.util.ShimmerLoadingAnimation

@Composable
fun ItemScreen(
    state: ItemScreenState,
    tagInputState: TextFieldState,
    saveItem: () -> Unit,
    showGalleryActivity: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 50.dp)
    ) {
        Box(
            modifier = Modifier
                .width(100.dp)
                .height(50.dp)
                .background(Color.Gray)
                .clickable { showGalleryActivity.invoke() }
        ) {
            Text(text = "go to gallery")
        }
        Spacer(modifier = Modifier.height(20.dp))
        Box(
            modifier = Modifier.size(200.dp)
        ) {
            SubcomposeAsyncImage(
                model = state.imageUri,
                contentDescription = "gallery image",
                modifier = Modifier.aspectRatio(1f),
                contentScale = ContentScale.Crop,
                loading = { ShimmerLoadingAnimation() },
                error = { ShimmerLoadingAnimation() }
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        ItemTextField(
            textState = tagInputState,
            hintText = "태그",
        )
        Spacer(modifier = Modifier.height(20.dp))
        Box(
            modifier = Modifier
                .width(100.dp)
                .height(50.dp)
                .background(Color.Gray)
                .clickable { saveItem.invoke() }
        ) {
            Text(text = "save")
        }
    }
}

@DevicePreviews
@Composable
fun ItemScreenPreview() {
    ItemScreen(
        state = ItemScreenState(),
        tagInputState = TextFieldState(),
        showGalleryActivity = {},
        saveItem = {}
    )
}