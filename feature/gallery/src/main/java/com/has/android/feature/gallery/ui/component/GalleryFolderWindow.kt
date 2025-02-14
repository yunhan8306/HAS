package com.has.android.feature.gallery.ui.component

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.has.android.core.model.ImageFolder
import com.has.android.design_system.ui.component.text.HasText
import com.has.android.design_system.util.ShimmerLoadingAnimation
import com.has.android.feature.gallery.GalleryAction

@Composable
fun GalleryFolderWindow(
    isShow: Boolean,
    imageFolderList: List<ImageFolder>,
    onAction: (GalleryAction) -> Unit,
    dismiss: () -> Unit
) {
    if(isShow) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Box(modifier = Modifier.height(52.dp))
            LazyColumn(
                modifier = Modifier.weight(1f).background(Color(0xFF202020))
            ) {
                items(imageFolderList) { imageFolder ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(75.dp)
                            .padding(horizontal = 16.dp)
                            .clickable {
                                onAction.invoke(GalleryAction.SelectFolder(imageFolder))
                                dismiss.invoke()
                            }
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(74.dp)
                                .padding(vertical = 12.dp),
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .clip(shape = RoundedCornerShape(8.dp))
                                        .size(50.dp)
                                ) {
                                    SubcomposeAsyncImage(
                                        model = imageFolder.uri,
                                        contentDescription = "gallery image",
                                        modifier = Modifier.aspectRatio(1f),
                                        contentScale = ContentScale.Crop,
                                        loading = { ShimmerLoadingAnimation() },
                                        error = { ShimmerLoadingAnimation() }
                                    )
                                }
                                Column(
                                    modifier = Modifier.padding(start = 12.dp),
                                ) {
                                    HasText(
                                        text = imageFolder.name,
                                        color = Color.White
                                    )
                                    HasText(
                                        modifier = Modifier.padding(top = 8.dp),
                                        text = imageFolder.cnt.toString(),
                                        color = Color(0xFF909090)
                                    )
                                }

                            }
                        }
                        Box(modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(color = Color(0xFF404040)))
                    }
                }
            }
        }
        BackHandler(onBack = dismiss)
    }
}