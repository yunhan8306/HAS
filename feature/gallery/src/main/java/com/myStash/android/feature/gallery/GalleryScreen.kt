package com.myStash.android.feature.gallery

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
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
import com.myStash.android.design_system.ui.DevicePreviews
import com.myStash.android.design_system.ui.component.header.HasHeader
import com.myStash.android.design_system.ui.component.text.HasText
import com.myStash.android.design_system.ui.theme.clickableNoRipple
import com.myStash.android.design_system.util.ShimmerLoadingAnimation

@Composable
fun GalleryScreen(
    focusImage: Image?,
    complete: () -> Unit,
    onBack: () -> Unit,
    sheetContent: @Composable (ColumnScope.() -> Unit),
) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp
    val sheetPeekHeight = screenHeight - screenWidth

    BottomSheetScaffold(
        sheetPeekHeight = sheetPeekHeight,
        sheetContent = sheetContent,
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
                            .width(100.dp)
                            .height(26.dp)
                            .background(
                                color = Color(0xFF202020),
                                shape = RoundedCornerShape(size = 13.dp)
                            )
                            .clip(shape = RoundedCornerShape(size = 13.dp))
                            .clickable { },
                        contentAlignment = Alignment.Center
                    ) {
                        Row {
                            HasText(
                                text = "최근 항목",
                                color = Color.White,
                                fontSize = 16.dp
                            )
                            Image(
                                modifier = Modifier.size(20.dp),
                                painter = painterResource(id = R.drawable.btn_gallery_down),
                                contentDescription = "gallery folder down"
                            )
                        }
                    }
                },
                endContent = {
                    Box(
                        modifier = Modifier
                            .padding(4.dp)
                            .clickableNoRipple { complete.invoke() }
                    ) {
                        HasText(
                            text = "등록",
                            color = Color(0xFFE4F562),
                            fontSize = 16.dp
                        )
                    }
                },
                onBack = onBack
            )
            Box(modifier = Modifier.height(12.dp))
            SubcomposeAsyncImage(
                model = focusImage?.uri,
                contentDescription = "gallery image",
                modifier = Modifier.fillMaxWidth().height(screenWidth),
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
        focusImage = null,
        complete = {},
        onBack = {},
        sheetContent = {}
    )
}
