package com.myStash.android.design_system.ui.component.has

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.myStash.android.common.resource.R
import com.myStash.android.common.util.isNotNull
import com.myStash.android.core.model.Has
import com.myStash.android.core.model.Tag
import com.myStash.android.design_system.ui.color.ColorFamilyGray300AndGray400
import com.myStash.android.design_system.ui.color.Purple
import com.myStash.android.design_system.ui.color.White
import com.myStash.android.design_system.ui.component.balloon.HasBalloon
import com.myStash.android.design_system.ui.component.balloon.HasBalloonItem
import com.myStash.android.design_system.ui.component.balloon.HasBalloonState
import com.myStash.android.design_system.ui.component.balloon.rememberHasBalloonBuilder
import com.myStash.android.design_system.ui.component.tag.TagHasChipItem
import com.myStash.android.design_system.ui.component.text.HasFontWeight
import com.myStash.android.design_system.ui.component.text.HasText
import com.myStash.android.design_system.ui.theme.clickableNoRipple
import com.myStash.android.design_system.util.ShimmerLoadingAnimation
import com.myStash.android.design_system.util.singleClick
import kotlinx.coroutines.delay

@Composable
fun HasMainItem(
    has: Has,
    isSelectedMode: Boolean,
    selectedNumber: Int?,
    tagList: List<Tag>,
    selectTagList: List<Tag>,
    onSelectHas: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {

    var balloonEvent by remember { mutableStateOf(HasBalloonState.NONE) }
    val balloonBuilder = rememberHasBalloonBuilder(
        onDismiss = { balloonEvent = HasBalloonState.NONE }
    )
    val balloonMenuList = remember {
        listOf(
            HasBalloonItem(
                name = "수정",
                onClick = {
                    balloonEvent = HasBalloonState.CLOSE
                    onEdit.invoke()
                }
            ),
            HasBalloonItem(
                name = "삭제",
                onClick = { balloonEvent = HasBalloonState.CLOSE
                    onDelete.invoke()
                }
            )
        )
    }

    var shortClick by remember { mutableStateOf(false) }

    LaunchedEffect(shortClick) {
        if(shortClick) {
            delay(3000)
            shortClick = false
        }
    }

    singleClick { singleClickEventListener ->
        Box(
            modifier = Modifier
                .border(
                    width = if (selectedNumber.isNotNull()) 2.dp else 1.dp,
                    color = if (selectedNumber.isNotNull()) Purple else ColorFamilyGray300AndGray400,
                    shape = RoundedCornerShape(size = 12.dp)
                )
                .clip(shape = RoundedCornerShape(size = 12.dp))
                .combinedClickable(
                    onLongClick = {
                        singleClickEventListener.onClick {
                            if (isSelectedMode) shortClick =
                                !shortClick else onSelectHas.invoke()
                        }
                    },
                    onClick = {
                        singleClickEventListener.onClick {
                            if (isSelectedMode) onSelectHas.invoke() else shortClick =
                                !shortClick
                        }
                    },
                ),
            contentAlignment = Alignment.Center
        ) {
            SubcomposeAsyncImage(
                modifier = Modifier
                    .aspectRatio(158f / 210f)
                    .fillMaxSize()
                    .alpha(if (shortClick) 0.4f else 1f),
                loading = { ShimmerLoadingAnimation() },
                error = { ShimmerLoadingAnimation() },
                contentScale = ContentScale.Crop,
                model = has.imagePath, // 엑박 이미지 필요?
                contentDescription = "has image"
            )

            if(shortClick) {
                FlowRow(
                    modifier = Modifier.padding(top = 17.dp, bottom = 17.dp, start = 15.dp, end = 15.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    tagList.forEach { tag ->
                        TagHasChipItem(
                            name = tag.name,
                            isSelected = tag.checkSelected(selectTagList)
                        )
                    }
                }
            }
        }

        if(isSelectedMode) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                contentAlignment = Alignment.TopEnd
            ) {
                Box(
                    modifier = Modifier
                        .size(18.dp)
                        .clickable { singleClickEventListener.onClick { onSelectHas.invoke() } }
                    ,
                    contentAlignment = Alignment.Center
                ) {
                    if(selectedNumber.isNotNull()) {
                        Image(
                            painter = painterResource(id = R.drawable.btn_purple_select),
                            contentDescription = "has select"
                        )
                        HasText(
                            text = selectedNumber.toString(),
                            color = White,
                            fontSize = 12.dp,
                            fontWeight = HasFontWeight.Bold
                        )
                    } else {
                        Image(
                            painter = painterResource(id = R.drawable.btn_purple_no_select),
                            contentDescription = "has select"
                        )
                    }
                }
            }
        } else {
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
                                .clickableNoRipple {
                                    singleClickEventListener.onClick {
                                        balloonEvent = HasBalloonState.OPEN
                                    }
                                }
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.btn_more_light),
                                contentDescription = "has more"
                            )
                        }
                    }
                }
            )
        }
    }
}