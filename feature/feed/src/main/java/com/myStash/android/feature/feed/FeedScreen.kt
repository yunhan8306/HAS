package com.myStash.android.feature.feed

import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import coil.compose.SubcomposeAsyncImage
import com.google.accompanist.navigation.animation.composable
import com.myStash.android.common.resource.R
import com.myStash.android.common.util.CommonActivityResultContract
import com.myStash.android.common.util.isNotNull
import com.myStash.android.core.model.Feed
import com.myStash.android.core.model.StyleScreenModel
import com.myStash.android.design_system.animation.slideIn
import com.myStash.android.design_system.ui.color.ColorFamilyGray100AndGray800
import com.myStash.android.design_system.ui.color.ColorFamilyGray500AndGray900
import com.myStash.android.design_system.ui.component.balloon.HasBalloon
import com.myStash.android.design_system.ui.component.balloon.HasBalloonItem
import com.myStash.android.design_system.ui.component.balloon.HasBalloonState
import com.myStash.android.design_system.ui.component.balloon.rememberHasBalloonBuilder
import com.myStash.android.design_system.ui.component.calender.HasCalender
import com.myStash.android.design_system.ui.component.dialog.HasConfirmDialog
import com.myStash.android.design_system.ui.component.header.HasLogoHeader
import com.myStash.android.design_system.ui.component.tag.TagChipItem
import com.myStash.android.design_system.ui.component.tag.TagMoreChipItem
import com.myStash.android.design_system.ui.component.text.HasFontWeight
import com.myStash.android.design_system.ui.component.text.HasText
import com.myStash.android.design_system.ui.theme.clickableNoRipple
import com.myStash.android.design_system.util.ShimmerLoadingAnimation
import com.myStash.android.feature.item.ItemActivity
import com.myStash.android.feature.item.ItemConstants
import com.myStash.android.feature.item.feed.AddStyleHasItem
import com.myStash.android.feature.item.item.ItemTab
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
    val activity = LocalContext.current as ComponentActivity

    val itemActivityLauncher = rememberLauncherForActivityResult(
        contract = CommonActivityResultContract(),
        onResult = {}
    )

    var deleteFeedConfirm: Feed? by remember { mutableStateOf(null) }

    FeedScreen(
        state = state,
        onAction = { action ->
            when(action) {
                is FeedScreenAction.Edit -> {
                    val intent = Intent(activity, ItemActivity::class.java)
                        .putExtra(ItemConstants.CMD_TAB_NAME, ItemTab.FEED.name)
                        .putExtra(ItemConstants.CMD_EDIT_TAB_NAME, ItemTab.FEED.name)
                        .putExtra(ItemConstants.CMD_STYLE_ID, state.selectedFeed?.styleId)
                        .putExtra(ItemConstants.CMD_FEED, state.selectedFeed)
                    itemActivityLauncher.launch(intent)
                    activity.slideIn()
                }
                is FeedScreenAction.Delete -> {
                    deleteFeedConfirm = state.selectedFeed
                }
                else -> viewModel.onAction(action)
            }
        }
    )

    HasConfirmDialog(
        isShow = deleteFeedConfirm.isNotNull(),
        title = "Feed",
        content = "Do you want to delete this Feed?",
        confirmText = "confirm",
        dismissText = "cancel",
        onConfirm = {
            viewModel.onAction(FeedScreenAction.Delete)
            deleteFeedConfirm = null
        },
        onDismiss = { deleteFeedConfirm = null }
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

    var balloonEvent by remember { mutableStateOf(HasBalloonState.NONE) }
    val balloonBuilder = rememberHasBalloonBuilder(
        onDismiss = { balloonEvent = HasBalloonState.NONE },
        block = {
            setMarginRight(16)
        }
    )
    val balloonMenuList = remember {
        listOf(
            HasBalloonItem(
                name = "수정",
                onClick = {
                    balloonEvent = HasBalloonState.CLOSE
                    onAction.invoke(FeedScreenAction.Edit)
                }
            ),
            HasBalloonItem(
                name = "삭제",
                onClick = {
                    balloonEvent = HasBalloonState.CLOSE
                    onAction.invoke(FeedScreenAction.Delete)
                }
            )
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        HasLogoHeader()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(ColorFamilyGray100AndGray800)
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
                        .clip(shape = RoundedCornerShape(size = 12.dp)),
                    year = state.calenderDate.year,
                    month = state.calenderDate.monthValue,
                    selectDate = state.selectedDate,
                    calenderDataList = state.calenderDataList,
                    onPrevMonth = { onAction.invoke(FeedScreenAction.PrevMonth) },
                    onNextMonth = { onAction.invoke(FeedScreenAction.NextMonth) },
                    onClickDay = { onAction.invoke(FeedScreenAction.SelectDay(it)) },
                )
            }
            Box(
                modifier = Modifier.padding(vertical = 16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(size = 12.dp))
                        .background(MaterialTheme.colors.background)
                        .fillMaxWidth()
                        .height(44.dp)
                        .padding(start = 16.dp, end = 12.dp)
                        .onGloballyPositioned { coordinates ->
                            yPosition = coordinates.positionInWindow().y.toInt()
                        },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    HasText(
                        modifier = Modifier.weight(1f),
                        text = "${state.selectedDate.monthValue}.${state.selectedDate.dayOfMonth}"
                    )
                    if(!headerToggle && state.selectedFeed.isNotNull()) {
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
                                Image(
                                    modifier = Modifier.clickableNoRipple { balloonEvent = HasBalloonState.OPEN },
                                    painter = painterResource(id = if(isSystemInDarkTheme()) R.drawable.btn_more_dark else R.drawable.btn_more_light),
                                    contentDescription = "feed more"
                                )
                            }
                        )
                    }
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
                        .clip(shape = RoundedCornerShape(size = 12.dp))
                        .background(MaterialTheme.colors.background)
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
                                cnt = "${state.selectedFeedTagList.size + (if(!flowToggle) -3 else 0)}",
                                isFold = flowToggle,
                                onClick = { flowToggle = !flowToggle }
                            )
                        }
                    }
                }
                Box(modifier = Modifier.height(16.dp))
                state.selectedFeedStyle?.let { style ->
                    Column(
                        modifier = Modifier
                            .clip(shape = RoundedCornerShape(size = 12.dp))
                            .background(MaterialTheme.colors.background)
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
            } ?: run {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = if(isSystemInDarkTheme()) R.drawable.img_hadung_empty_dark else R.drawable.img_hadung_empty_light),
                            contentDescription = "feed gone"
                        )
                        HasText(
                            modifier = Modifier.padding(top = 16.dp),
                            text = "There is no registered Feed.",
                            color = ColorFamilyGray500AndGray900,
                            fontSize = 14.dp
                        )
                    }
                }
            }
        }
    }
    if(headerCalenderFade > 0f && state.selectedFeed.isNotNull()) {
        Column(
            modifier = Modifier.alpha(headerCalenderFade)
        ) {
            Box(modifier = Modifier.height(40.dp))
            Row(
                modifier = Modifier
                    .background(MaterialTheme.colors.background)
                    .fillMaxWidth()
                    .height(44.dp)
                    .padding(start = 16.dp, end = 12.dp)
                    .clickableNoRipple { scope.launch { scrollState.animateScrollTo(0) } },
                verticalAlignment = Alignment.CenterVertically
            ) {
                HasText(
                    modifier = Modifier.weight(1f),
                    text = "${state.selectedDate.monthValue}.${state.selectedDate.dayOfMonth}"
                )
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
                        Image(
                            modifier = Modifier.clickableNoRipple { balloonEvent = HasBalloonState.OPEN },
                            painter = painterResource(id = if(isSystemInDarkTheme()) R.drawable.btn_more_dark else R.drawable.btn_more_light),
                            contentDescription = "feed more"
                        )
                    }
                )
            }
        }
    }
}