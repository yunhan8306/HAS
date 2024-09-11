package com.myStash.android.feature.gallery

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.myStash.android.common.resource.R
import com.myStash.android.common.util.isNotNull
import com.myStash.android.design_system.ui.component.text.HasText
import com.myStash.android.design_system.ui.theme.clickableNoRipple
import com.myStash.android.design_system.util.ShimmerLoadingAnimation
import com.myStash.android.design_system.util.singleClick


@Composable
fun GalleryItem(
    isSingle: Boolean,
    imageUri: Uri,
    selectedNumber: Int?,
    onClick: () -> Unit,
    onFocus: () -> Unit
) {
    singleClick { singleClickEventListener ->
        Box(
            modifier = if(selectedNumber.isNotNull()) {
                Modifier
                    .border(width = 1.dp, color = Color(0xFFE4F562))
                    .background(color = Color(0x80000000))
                    .clickable { singleClickEventListener.onClick { onFocus.invoke() } }
            } else {
                Modifier.clickable { onFocus.invoke() }
            },
            contentAlignment = Alignment.TopEnd
        ) {
            SubcomposeAsyncImage(
                model = imageUri,
                contentDescription = "gallery image",
                modifier = Modifier.aspectRatio(1f),
                contentScale = ContentScale.Crop,
                loading = { ShimmerLoadingAnimation() },
                error = { ShimmerLoadingAnimation() }
            )

            Box(
                modifier = Modifier
                    .padding(6.dp)
                    .clickableNoRipple { singleClickEventListener.onClick { onClick.invoke() } },
                contentAlignment = Alignment.Center
            ) {
                if(selectedNumber.isNotNull()) {
                    if(isSingle) {
                        Image(
                            painter = painterResource(id = R.drawable.btn_gallery_select_single),
                            contentDescription = "select single"
                        )
                    } else {
                        Image(
                            painter = painterResource(id = R.drawable.btn_gallery_select_multi),
                            contentDescription = "select multi"
                        )
                        HasText(
                            text = selectedNumber.toString(),
                            fontSize = 12.dp
                        )
                    }
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.btn_gallery_no_select),
                        contentDescription = ""
                    )
                }
            }
        }
    }
}