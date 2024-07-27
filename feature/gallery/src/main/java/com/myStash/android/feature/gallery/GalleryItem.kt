package com.myStash.android.feature.gallery

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.myStash.android.common.util.isNotNull
import com.myStash.android.common.resource.R
import com.myStash.android.design_system.ui.component.text.HasText
import com.myStash.android.design_system.util.ShimmerLoadingAnimation


@Composable
fun GalleryItem(
    isSingle: Boolean,
    imageUri: Uri,
    selectedNumber: Int?,
    onClick: () -> Unit,
    onFocus: () -> Unit
) {
    Box(
        modifier = if(selectedNumber.isNotNull()) {
            Modifier
                .border(width = 1.dp, color = Color(0xFFE4F562))
                .background(color = Color(0x80000000))
                .clickable { onFocus.invoke() }
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
                .clickable { onClick.invoke() },
            contentAlignment = Alignment.Center
        ) {
            if(selectedNumber.isNotNull()) {
                if(isSingle) {
                    Image(
                        modifier = Modifier.size(18.dp),
                        painter = painterResource(id = R.drawable.btn_tag_select),
                        contentDescription = ""
                    )
                } else {
                    Image(
                        modifier = Modifier.size(18.dp),
                        painter = painterResource(id = R.drawable.btn_gallery_multi_selector),
                        contentDescription = ""
                    )
                    HasText(
                        text = selectedNumber.toString(),
                        fontSize = 12.dp
                    )
                }
            } else {
                Image(
                    modifier = Modifier.size(18.dp),
                    painter = painterResource(id = R.drawable.btn_no_select_has),
                    contentDescription = ""
                )
            }
        }
    }
}