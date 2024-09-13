package com.myStash.android.feature.gallery.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetScaffoldState
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.myStash.android.common.resource.R
import com.myStash.android.core.model.Image
import com.myStash.android.core.model.ImageFolder
import com.myStash.android.design_system.ui.DevicePreviews
import com.myStash.android.design_system.ui.component.header.HasHeader
import com.myStash.android.design_system.ui.component.snackbar.HasSnackBar
import com.myStash.android.design_system.ui.component.text.HasText
import com.myStash.android.design_system.ui.theme.clickableNoRipple
import com.myStash.android.design_system.util.ShimmerLoadingAnimation
import com.myStash.android.feature.gallery.GalleryAction

@Composable
fun GalleryScreen(
    scaffoldState: BottomSheetScaffoldState,
    focusImage: Image?,
    selectedFolder: ImageFolder,
    isShowFolderWindow: Boolean,
    onSelectFolder: () -> Unit,
    onAction: (GalleryAction) -> Unit,
    onBack: () -> Unit,
    sheetContent: @Composable (ColumnScope.() -> Unit),
) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp
    val sheetPeekHeight = screenHeight - screenWidth

    BottomSheetScaffold(
        sheetContent = sheetContent,
        scaffoldState = scaffoldState,
        snackbarHost = { HasSnackBar(it) },
        sheetPeekHeight = sheetPeekHeight,
        sheetBackgroundColor = Color.White,
        sheetElevation = 0.dp,
        backgroundColor = Color.Transparent,
        contentColor = Color.White
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .background(Color.Black)
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            HasHeader(
                modifier = Modifier.background(Color.Black),
                centerContent = {
                    Box(
                        modifier = Modifier
                            .height(26.dp)
                            .background(
                                color = Color(0xFF202020),
                                shape = RoundedCornerShape(size = 13.dp)
                            )
                            .clip(shape = RoundedCornerShape(size = 13.dp))
                            .clickable { onSelectFolder.invoke() },
                        contentAlignment = Alignment.Center
                    ) {
                        Row(
                            modifier = Modifier.padding(start = 12.dp, end = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            HasText(
                                text = selectedFolder.name,
                                color = Color.White,
                                fontSize = 16.dp
                            )
                            Image(
                                painter = painterResource(id = if(isShowFolderWindow) R.drawable.img_up_gallery else R.drawable.img_down_gallery),
                                contentDescription = "gallery folder toggle"
                            )
                        }
                    }
                },
                endContent = {
                    if(!isShowFolderWindow) {
                        Box(
                            modifier = Modifier
                                .padding(4.dp)
                                .clickableNoRipple { onAction.invoke(GalleryAction.Complete) }
                        ) {
                            HasText(
                                text = "등록",
                                color = Color(0xFFE4F562),
                                fontSize = 16.dp
                            )
                        }
                    }
                },
                onBack = onBack
            )
            Box(modifier = Modifier.height(12.dp))
            SubcomposeAsyncImage(
                model = focusImage?.uri,
                contentDescription = "gallery image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(screenWidth),
                contentScale = ContentScale.Crop,
                loading = { ShimmerLoadingAnimation() },
                error = { ShimmerLoadingAnimation() }
            )
        }
    }
}

@DevicePreviews
@Composable
fun GalleryScreenPreview() {
    GalleryScreen(
        scaffoldState = rememberBottomSheetScaffoldState(),
        focusImage = null,
        selectedFolder = ImageFolder(),
        isShowFolderWindow = false,
        onSelectFolder = {},
        onAction = {},
        onBack = {},
        sheetContent = {}
    )
}