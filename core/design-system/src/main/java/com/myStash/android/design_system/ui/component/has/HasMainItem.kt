package com.myStash.android.design_system.ui.component.has

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.myStash.android.common.resource.R
import com.myStash.android.common.util.isNotNull
import com.myStash.android.core.model.Has
import com.myStash.android.core.model.Tag
import com.myStash.android.design_system.ui.color.Black
import com.myStash.android.design_system.ui.color.ColorFamilyGray300AndGray400
import com.myStash.android.design_system.ui.color.Purple
import com.myStash.android.design_system.ui.color.White
import com.myStash.android.design_system.ui.component.HasBalloon
import com.myStash.android.design_system.ui.component.rememberHasBalloonBuilder
import com.myStash.android.design_system.ui.component.tag.TagHasChipItem
import com.myStash.android.design_system.ui.component.text.HasFontWeight
import com.myStash.android.design_system.ui.component.text.HasText
import com.myStash.android.design_system.ui.theme.clickableNoRipple
import com.myStash.android.design_system.util.ShimmerLoadingAnimation
import com.myStash.android.design_system.util.clickableRipple
import com.myStash.android.design_system.util.singleClick
import com.skydoves.balloon.compose.Balloon
import com.skydoves.balloon.compose.rememberBalloonBuilder
import com.skydoves.balloon.compose.setBackgroundColor
import kotlinx.coroutines.delay

@Composable
fun HasMainItem(
    has: Has,
    isSelectedMode: Boolean,
    selectedNumber: Int?,
    tagList: List<Tag>,
    selectTagList: List<Tag>,
    onSelectHas: (Has) -> Unit,
    onEditHas: (Has) -> Unit,
    onDeleteHas: (Has) -> Unit
) {
    val hasBalloon = rememberHasBalloonBuilder()
    var balloonEvent by remember { mutableStateOf(TimeLineMenuEvent.NONE) }
    val builder = rememberBalloonBuilder {
        arrowSize = 0
        setPadding(4)
        setMarginLeft(90)
        setBackgroundColor(Color.Transparent)
        setOnBalloonDismissListener {
            balloonEvent = TimeLineMenuEvent.NONE
        }
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
                                !shortClick else onSelectHas.invoke(has)
                        }
                    },
                    onClick = {
                        singleClickEventListener.onClick {
                            if (isSelectedMode) onSelectHas.invoke(has) else shortClick =
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
                        .clickable { singleClickEventListener.onClick { onSelectHas.invoke(has) } }
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
            Balloon(
                modifier = Modifier
                    .padding(end = 10.dp),
                builder = builder,
                balloonContent = {
                    Box(
                        contentAlignment = Alignment.TopEnd
                    ) {
                        Box(modifier = Modifier
                            .size(80.dp)
                            .background(Green)) {
                            Log.d("qwe123", "HasBalloon")
                            HasText(
                                text = "수정",
                                color = Black
                            )
                            HasText(
                                text = "수정",
                                color = Black
                            )
                        }
                    }
                }, content = { balloonWindow ->
                    var size by remember { mutableStateOf(IntSize.Zero) }
                    var position by remember { mutableStateOf(Offset.Zero) }

                    LaunchedEffect(balloonEvent) {
                        when (balloonEvent) {
                            TimeLineMenuEvent.NONE -> Unit
                            TimeLineMenuEvent.CLOSE -> balloonWindow.dismiss()
                            TimeLineMenuEvent.OPEN -> balloonWindow.showAsDropDown(
//                                xOff = (position.x * 0.55).toInt(),
                                yOff = size.height * 2
                            )
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
                                        balloonEvent = TimeLineMenuEvent.OPEN
                                    }
                                }
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.btn_more_light),
                                contentDescription = "feed more"
                            )
                        }
                    }
                }
            )
        }
    }
}

internal enum class TimeLineMenuEvent {
    NONE, OPEN, CLOSE
}