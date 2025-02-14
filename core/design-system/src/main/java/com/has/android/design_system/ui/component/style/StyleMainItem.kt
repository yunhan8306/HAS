package com.has.android.design_system.ui.component.style

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.has.android.common.resource.R
import com.has.android.core.model.Has
import com.has.android.core.model.StyleScreenModel
import com.has.android.design_system.ui.color.ColorFamilyGray300AndGray400
import com.has.android.design_system.ui.color.Purple
import com.has.android.design_system.ui.component.balloon.HasBalloon
import com.has.android.design_system.ui.component.balloon.HasBalloonItem
import com.has.android.design_system.ui.component.balloon.HasBalloonState
import com.has.android.design_system.ui.component.balloon.rememberHasBalloonBuilder
import com.has.android.design_system.ui.theme.clickableNoRipple
import com.has.android.design_system.util.ShimmerLoadingAnimation

@Composable
fun StyleMainItem(
    style: StyleScreenModel,
    isSelected: Boolean,
    isMain: Boolean = false,
    onClick: (StyleScreenModel) -> Unit,
    onLongClick: (StyleScreenModel) -> Unit,
    onEdit: (StyleScreenModel) -> Unit = {},
    onDelete: (StyleScreenModel) -> Unit = {}
) {
    var balloonEvent by remember { mutableStateOf(HasBalloonState.NONE) }
    val balloonBuilder = rememberHasBalloonBuilder(
        onDismiss = { balloonEvent = HasBalloonState.NONE }
    )
    val balloonMenuList = remember(style) {
        listOf(
            HasBalloonItem(
                name = "수정",
                onClick = {
                    balloonEvent = HasBalloonState.CLOSE
                    onEdit.invoke(style)
                }
            ),
            HasBalloonItem(
                name = "삭제",
                onClick = {
                    balloonEvent = HasBalloonState.CLOSE
                    onDelete.invoke(style)
                }
            )
        )
    }

    Box(
        modifier = Modifier
            .border(
                width = if (isSelected) 2.dp else 1.dp,
                color = if (isSelected) Purple else ColorFamilyGray300AndGray400,
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
                key = { _, has -> has.id ?: -1 }
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
                    Box(
                        modifier = Modifier
                            .aspectRatio(79 / 105f)
                            .background(MaterialTheme.colors.background)
                    )
                }
            }
        }
    }
    if(isMain && !isSelected) {
        HasBalloon(
            builder = balloonBuilder,
            menuList = balloonMenuList,
            content = { balloonWindow ->
                LaunchedEffect(balloonEvent) {
                    when (balloonEvent) {
                        HasBalloonState.NONE -> Unit
                        HasBalloonState.CLOSE -> balloonWindow.dismiss()
                        HasBalloonState.OPEN -> balloonWindow.showAsDropDown()
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 4.dp),
                    contentAlignment = Alignment.TopEnd
                ) {
                    Box(
                        modifier = Modifier
                            .padding(6.dp)
                            .clickableNoRipple { balloonEvent = HasBalloonState.OPEN }
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.btn_more_light),
                            contentDescription = "style more"
                        )
                    }
                }
            }
        )
    }
}