package com.myStash.feature.gallery

import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.SubcomposeAsyncImage
import com.myStash.core.model.Image
import com.myStash.design_system.ui.theme.clickableNoRipple
import com.myStash.design_system.util.ShimmerLoadingAnimation
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun GalleryRoute(
    viewModel: GalleryViewModel = hiltViewModel(),
    complete: () -> Unit,
) {
    val state by viewModel.collectAsState()

    var zoomImage: Image? by remember { mutableStateOf(null) }

    viewModel.collectSideEffect { sideEffect ->
        when(sideEffect) {
            is GallerySideEffect.Zoom -> zoomImage = sideEffect.image
        }
    }

    GalleryScreen(
        imageList = state.imageList,
        selectedImageList = state.selectedImageList,
        onEvent = viewModel::event,
        complete = complete
    )


    zoomImage?.let { image ->
        val selectedNumber: Int? by remember(state.selectedImageList) {
            derivedStateOf {
                val index = state.selectedImageList.indexOfFirst { it.uri == image.uri }
                if(index != -1) index + 1 else null
            }
        }

        ZoomScreen(
            image = image,
            selectedNumber = selectedNumber,
            onEvent = viewModel::event,
            onBack = { zoomImage = null }
        )
    }
}

@Composable
fun ZoomScreen(
    image: Image,
    selectedNumber: Int?,
    onEvent: (GalleryEvent) -> Unit,
    onBack: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .clickableNoRipple()
    ) {

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("zoom image")
            Spacer(modifier = Modifier.weight(1f))
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(if (selectedNumber == null) Color.White else Color.Red)
                    .clickable { onEvent.invoke(GalleryEvent.OnClick(image)) }
            ) {
                selectedNumber?.let {
                    Text(selectedNumber.toString())
                }
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        SubcomposeAsyncImage(
            model = image.uri,
            contentDescription = "zoom image",
            modifier = Modifier.aspectRatio(1f),
            contentScale = ContentScale.Crop,
            loading = { ShimmerLoadingAnimation() },
            error = { ShimmerLoadingAnimation() }
        )
        Spacer(modifier = Modifier.height(20.dp))
    }

    BackHandler(onBack = onBack)
}