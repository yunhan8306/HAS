package com.myStash.android.feature.feed

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import coil.compose.SubcomposeAsyncImage
import com.google.accompanist.navigation.animation.composable
import com.myStash.android.design_system.ui.component.calender.HasCalender
import com.myStash.android.design_system.ui.component.header.HasLogoHeader
import com.myStash.android.design_system.ui.component.tag.TagChipItem
import com.myStash.android.design_system.ui.component.tag.TagMoreChipItem
import com.myStash.android.design_system.ui.component.text.HasFontWeight
import com.myStash.android.design_system.ui.component.text.HasText
import com.myStash.android.design_system.ui.theme.clickableNoRipple
import com.myStash.android.design_system.util.ShimmerLoadingAnimation
import com.myStash.android.feature.item.feed.AddStyleHasItem
import com.myStash.android.navigation.MainNavType
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.compose.collectAsState

fun NavGraphBuilder.feedScreen() {
    composable(
        route = MainNavType.FEED.name
    ) {
        FeedRoute()
    }
}

@Composable
fun FeedRoute(
    viewModel: FeedViewModel = hiltViewModel()
) {
    val state by viewModel.collectAsState()

    FeedScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
fun FeedScreen(
    state: FeedScreenState,
    onAction: (FeedScreenAction) -> Unit,
) {

    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollState()
    val tagScrollState = rememberScrollState()
    var flowToggle by remember { mutableStateOf(false) }
    var yPosition by remember { mutableIntStateOf(0) }
    val headerToggle by remember(yPosition) { derivedStateOf { yPosition < 140 } }
    val headerCalenderFade by animateFloatAsState(
        targetValue = if(headerToggle) 1f else 0f,
        animationSpec = tween(400),
        label = "header calender fade ani"
    )

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        HasLogoHeader()
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(Color(0xFFF5F5F5))
                .padding(horizontal = 16.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .widthIn(max = 400.dp)
                    .heightIn(max = 400.dp)
            ) {
                HasCalender(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = Color(0xFFFFFFFF),
                            shape = RoundedCornerShape(size = 12.dp)
                        ),
                    year = state.calenderDate.year,
                    month = state.calenderDate.monthValue,
                    selectDate = state.selectedDate,
                    calenderDataList = state.calenderDataList,
                    onClickAgoCalender = { onAction.invoke(FeedScreenAction.AgoCalender) },
                    onClickNextCalender = { onAction.invoke(FeedScreenAction.NextCalender) },
                    onClickDay = { onAction.invoke(FeedScreenAction.SelectDay(it)) },
                )
            }
            Box(
                modifier = Modifier.padding(vertical = 16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp)
                        .background(
                            color = Color(0xFFFFFFFF),
                            shape = RoundedCornerShape(size = 12.dp)
                        )
                        .onGloballyPositioned { coordinates ->
                            yPosition = coordinates.positionInWindow().y.toInt()
                        },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    HasText(
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 16.dp),
                        text = "${state.selectedDate.monthValue}.${state.selectedDate.dayOfMonth}"
                    )
                    Box(
                        modifier = Modifier
                            .background(Color.Blue)
                            .size(20.dp)
                            .padding(end = 10.dp)
                    )
                }
            }
            
            state.selectedFeed?.let { feed ->
                Box {
                    SubcomposeAsyncImage(
                        model = feed.images.firstOrNull(),
                        contentDescription = "gallery image",
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1f)
                            .clip(shape = RoundedCornerShape(size = 8.dp)),
                        contentScale = ContentScale.Crop,
                        loading = { ShimmerLoadingAnimation() },
                        error = { ShimmerLoadingAnimation() }
                    )
                }
                Box(modifier = Modifier.height(16.dp))
                Column(
                    modifier = Modifier
                        .background(
                            color = Color(0xFFFFFFFF),
                            shape = RoundedCornerShape(size = 12.dp)
                        )
                        .padding(top = 16.dp, bottom = 16.dp, start = 20.dp, end = 20.dp)
                        .heightIn(max = 300.dp)
                ) {
                    HasText(
                        text = "Tag",
                        fontSize = 18.dp,
                        fontWeight = HasFontWeight.Bold
                    )
                    Box(modifier = Modifier.height(20.dp))
                    FlowRow(
                        modifier = Modifier
                            .heightIn(max = if (flowToggle) 200.dp else Dp.Unspecified)
                            .fillMaxWidth()
                            .verticalScroll(tagScrollState)
                    ) {
                        state.selectedFeedTagList.forEachIndexed { index, tag ->
                            if(index < 3 || flowToggle) {
                                TagChipItem(
                                    name = tag.name,
                                    isSelected = true,
                                    onClick = {}
                                )
                            }
                        }
                        if(state.selectedFeedTagList.size > 3) {
                            TagMoreChipItem(
                                cnt = "${state.selectedFeedTagList.size - 4}",
                                isFold = !flowToggle,
                                onClick = { flowToggle = !flowToggle }
                            )
                        }
                    }
                }
                Box(modifier = Modifier.height(16.dp))
                state.selectedFeedStyle?.let { style ->
                    Column(
                        modifier = Modifier
                            .background(
                                color = Color(0xFFFFFFFF),
                                shape = RoundedCornerShape(size = 12.dp)
                            )
                            .padding(top = 16.dp, bottom = 16.dp, start = 20.dp, end = 20.dp)
                    ) {
                        LazyColumn(
                            modifier = Modifier.heightIn(max = 2000.dp),
                            userScrollEnabled = false
                        ) {
                            items(
                                items = style.hasList,
                                key = { it.id!! }
                            ) { has ->
                                AddStyleHasItem(
                                    has = has,
                                    typeTotalList = state.typeTotalList,
                                    tagTotalList = state.selectedFeedTagList
                                )
                            }
                        }
                    }
                    Box(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
    if(headerCalenderFade > 0f) {
        Column(
            modifier = Modifier.alpha(headerCalenderFade)
        ) {
            Box(modifier = Modifier.height(40.dp))
            Row(
                modifier = Modifier
                    .background(Color.White)
                    .fillMaxWidth()
                    .height(44.dp)
                    .clickableNoRipple { scope.launch { scrollState.animateScrollTo(0) } },
                verticalAlignment = Alignment.CenterVertically
            ) {
                HasText(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 16.dp),
                    text = "${state.selectedDate.monthValue}.${state.selectedDate.dayOfMonth}"
                )
                Box(
                    modifier = Modifier
                        .background(Color.Blue)
                        .size(20.dp)
                        .padding(end = 10.dp)
                )
            }
        }
    }
}