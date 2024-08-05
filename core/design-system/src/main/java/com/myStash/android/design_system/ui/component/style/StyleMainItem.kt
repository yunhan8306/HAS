package com.myStash.android.design_system.ui.component.style

import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.myStash.android.core.model.StyleScreenModel
import com.myStash.android.design_system.util.ShimmerLoadingAnimation

@Composable
fun StyleMainItem(
    style: StyleScreenModel,
    isSelected: Boolean,
    onClick: (StyleScreenModel) -> Unit,
    onLongClick: (StyleScreenModel) -> Unit
) {
    Box(
        modifier = Modifier
            .border(
                width = if (isSelected) 2.dp else 1.dp,
                color = if (isSelected) Color(0xFF716DF6) else Color(0xFFE1E1E1),
                shape = RoundedCornerShape(size = 12.dp)
            )
            .clip(RoundedCornerShape(size = 12.dp))
            .combinedClickable(
                onLongClick = { onLongClick.invoke(style) },
                onClick = { onClick.invoke(style) },
            )
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.heightIn(max = 400.dp)
        ) {
            itemsIndexed(
                items = style.hasList,
                key = { index, has -> has.id ?: -1 }
            ) { index, has ->
                if(index < 4) {
                    SubcomposeAsyncImage(
                        model = has.imagePath,
                        contentDescription = "feed image",
                        modifier = Modifier.aspectRatio(79 / 105f),
                        contentScale = ContentScale.Crop,
                        loading = { ShimmerLoadingAnimation() },
                        error = { ShimmerLoadingAnimation() }
                    )
                }
            }
            if(style.hasList.size < 4) {
                items(count = 4 - style.hasList.size) {
                    SubcomposeAsyncImage(
                        model = null,
                        contentDescription = "feed image",
                        modifier = Modifier.aspectRatio(79 / 105f),
                        contentScale = ContentScale.Crop,
                        loading = { ShimmerLoadingAnimation() },
                        error = { ShimmerLoadingAnimation() }
                    )
                }
            }
        }
    }
}