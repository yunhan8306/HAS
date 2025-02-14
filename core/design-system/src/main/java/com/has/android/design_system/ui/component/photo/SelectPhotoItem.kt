package com.has.android.design_system.ui.component.photo

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.has.android.common.resource.R
import com.has.android.design_system.ui.theme.clickableNoRipple
import com.has.android.design_system.util.ShimmerLoadingAnimation
import com.has.android.design_system.util.singleClick

@Composable
fun SelectPhotoItem(
    imageUri: String?,
    onClick: () -> Unit,
    onDelete: () -> Unit = {}
) {
    singleClick { singleClickEventListener ->
        Row {
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .clickable { singleClickEventListener.onClick { onClick.invoke() } },
                contentAlignment = Alignment.TopEnd
            ) {
                SubcomposeAsyncImage(
                    model = imageUri,
                    contentDescription = "select photo",
                    modifier = Modifier.aspectRatio(1f),
                    contentScale = ContentScale.Crop,
                    loading = { ShimmerLoadingAnimation() },
                    error = { ShimmerLoadingAnimation() }
                )
                Box(
                    modifier = Modifier.padding(4.dp)
                ) {
                    Image(
                        modifier = Modifier.clickableNoRipple { singleClickEventListener.onClick { onDelete.invoke() } },
                        painter = painterResource(id = R.drawable.btn_delete),
                        contentDescription = "delete"
                    )
                }
            }
            Box(modifier = Modifier.padding(end = 6.dp))
        }
    }
}