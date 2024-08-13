package com.myStash.android

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.myStash.android.common.resource.R
import com.myStash.android.core.model.Has
import com.myStash.android.design_system.ui.color.Gray300
import com.myStash.android.design_system.ui.component.button.HasSelectButton
import com.myStash.android.design_system.ui.theme.clickableNoRipple
import com.myStash.android.design_system.util.ShimmerLoadingAnimation

@Composable
fun MainHasBottomModal(
    hasList: List<Has>,
    onDelete: (Has) -> Unit,
    onSelect: () -> Unit,
    onCancel: () -> Unit,
) {
    var isShow by remember { mutableStateOf(false) }

    LaunchedEffect(hasList) {
        isShow = hasList.isNotEmpty()
    }

    if(isShow) {
        Column(
            modifier = Modifier
                .shadow(elevation = 10.dp, shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                .background(color = MaterialTheme.colors.surface, shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                .fillMaxWidth()
                .height(168.dp)
                .clickableNoRipple {  }
                .padding(14.dp)
        ) {
            LazyRow(
                modifier = Modifier
                    .height(68.dp)
                    .fillMaxWidth()
            ) {
                items(hasList) { has ->
                    Box(
                        modifier = Modifier
                            .padding(2.dp)
                            .clickable { onDelete.invoke(has) },
                        contentAlignment = Alignment.TopEnd
                    ) {
                        SubcomposeAsyncImage(
                            modifier = Modifier
                                .aspectRatio(1f)
                                .clip(shape = RoundedCornerShape(size = 6.dp))
                                .border(width = 1.dp, color = Gray300, shape = RoundedCornerShape(size = 6.dp))
                                .clip(RoundedCornerShape(size = 6.dp))
                                .size(68.dp),
                            loading = { ShimmerLoadingAnimation() },
                            error = { ShimmerLoadingAnimation() },
                            contentScale = ContentScale.Crop,
                            model = has.imagePath,
                            contentDescription = "main has modal image"
                        )
                        Image(
                            modifier = Modifier.size(24.dp),
                            painter = painterResource(id = R.drawable.btn_input_delete),
                            contentDescription = "delete"
                        )
                    }
                }
            }
            HasSelectButton(
                modifier = Modifier.padding(top = 16.dp),
                selectText = "Style 등록",
                onSelect = onSelect,
                onCancel = onCancel
            )
        }
    }
}