package com.myStash.android.feature.style

import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable
import com.myStash.android.common.compose.activityViewModel
import com.myStash.android.common.util.CommonActivityResultContract
import com.myStash.android.common.util.isNotNull
import com.myStash.android.core.model.StyleScreenModel
import com.myStash.android.core.model.testTagList
import com.myStash.android.design_system.animation.fadeIn
import com.myStash.android.design_system.animation.slideIn
import com.myStash.android.design_system.ui.DevicePreviews
import com.myStash.android.design_system.ui.component.SpacerLineBox
import com.myStash.android.design_system.ui.component.content.ContentHeaderSearchText
import com.myStash.android.design_system.ui.component.dialog.HasConfirmDialog
import com.myStash.android.design_system.ui.component.style.StyleMainItem
import com.myStash.android.design_system.ui.component.tag.TagChipItem
import com.myStash.android.design_system.ui.component.tag.TagMoreChipItem
import com.myStash.android.design_system.ui.component.text.HasText
import com.myStash.android.feature.item.ItemActivity
import com.myStash.android.feature.item.ItemConstants
import com.myStash.android.feature.item.item.ItemTab
import com.myStash.android.feature.search.SearchActivity
import com.myStash.android.feature.search.SearchConstants
import com.myStash.android.navigation.MainNavType
import org.orbitmvi.orbit.compose.collectAsState

fun NavGraphBuilder.styleScreen() {
    composable(
        route = MainNavType.STYLE.name
    ) {
        StyleRoute()
    }
}

@Composable
fun StyleRoute(
    viewModel: StyleViewModel = activityViewModel(),
) {

    val state by viewModel.collectAsState()

    val activity = LocalContext.current as ComponentActivity
    var showMoreStyle: StyleScreenModel? by remember { mutableStateOf(null) }

    val itemActivityLauncher = rememberLauncherForActivityResult(
        contract = CommonActivityResultContract(),
        onResult = {}
    )

    val searchActivityLauncher = rememberLauncherForActivityResult(
        contract = CommonActivityResultContract(),
        onResult = { intent ->
            intent?.getLongArrayExtra(SearchConstants.SELECTED_TAG_LIST)?.let {
                viewModel.onAction(StyleScreenAction.SetTagList(it.toList()))
            }
        }
    )

    var deleteStyleConfirm: StyleScreenModel? by remember { mutableStateOf(null) }

    StyleScreen(
        state = state,
        onAction = { action ->
            when(action) {
                is StyleScreenAction.ShowSearch -> {
                    val intent = Intent(activity, SearchActivity::class.java)
                        .putExtra(SearchConstants.TYPE, SearchConstants.STYLE)
                        .putExtra(SearchConstants.SELECTED_TAG_LIST, state.selectedTagList.map { it.id }.toTypedArray())

                    searchActivityLauncher.launch(intent)
                    activity.fadeIn()
                }
                is StyleScreenAction.ShowMoreStyle -> showMoreStyle = action.style
                is StyleScreenAction.ShowItemActivity -> {
                    val intent = Intent(activity, ItemActivity::class.java)
                        .putExtra(ItemConstants.CMD_TAB_NAME, ItemTab.STYLE.name)
                        .putExtra(ItemConstants.CMD_STYLE, action.style.hasList.map { it.id }.toTypedArray())
                        .putExtra(ItemConstants.CMD_STYLE_ID, action.style.id)
                        .putExtra(ItemConstants.CMD_EDIT_TAB_NAME, ItemTab.STYLE.name)
                    itemActivityLauncher.launch(intent)
                    activity.slideIn()
                }
                is StyleScreenAction.DeleteStyle -> {
                    deleteStyleConfirm = action.style
                }
                else -> viewModel.onAction(action)
            }
        }
    )

    StyleMoreDialog(
        styleScreenModel = showMoreStyle,
        typeTotalList = state.totalTypeList,
        tagTotalList = state.totalTagList,
        onDismiss = { showMoreStyle = null },
    )

    HasConfirmDialog(
        isShow = deleteStyleConfirm.isNotNull(),
        title = "Style",
        content = "Do you want to delete this Style?",
        confirmText = "confirm",
        dismissText = "cancel",
        onConfirm = {
            deleteStyleConfirm?.let { viewModel.onAction(StyleScreenAction.DeleteStyle(it)) }
            deleteStyleConfirm = null
        },
        onDismiss = { deleteStyleConfirm = null }
    )

    if(state.selectedStyle != null) {
        BackHandler {
            viewModel.onAction(StyleScreenAction.ResetSelectStyle)
        }
    }
}

@Composable
fun StyleScreen(
    state: StyleScreenState,
    onAction: (StyleScreenAction) -> Unit,
) {

    val tagScrollState = rememberScrollState()

    LaunchedEffect(state.totalTagList) {
        tagScrollState.scrollTo(0)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(top = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        ContentHeaderSearchText(
            text = "원하는 태그를 검색해 보세요",
            onClick = { onAction.invoke(StyleScreenAction.ShowSearch) }
        )
        FlowRow(
            modifier = Modifier
                .heightIn(max = if (state.isFoldTag) 200.dp else Dp.Unspecified)
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 8.dp, start = 16.dp, end = 16.dp)
                .verticalScroll(tagScrollState)
        ) {
            state.totalTagList.forEachIndexed { index, tag ->
                val isSelected by remember(state.selectedTagList, state.totalTagList) {
                    derivedStateOf {
                        tag.checkSelected(state.selectedTagList)
                    }
                }
                if(index < 3 || state.isFoldTag) {
                    TagChipItem(
                        name = tag.name,
                        isSelected = isSelected,
                        onClick = { onAction.invoke(StyleScreenAction.SelectTag(tag)) }
                    )
                }
            }
            TagMoreChipItem(
                cnt = "${state.totalTagList.size + (if(!state.isFoldTag) -3 else 0)}",
                isFold = state.isFoldTag,
                onClick = { onAction.invoke(StyleScreenAction.TagToggle) }
            )
        }
        SpacerLineBox()
        if(state.styleList.isNotEmpty()) {
            LazyVerticalGrid(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .weight(1f)
                    .fillMaxWidth(),
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(
                    count = 2,
                    key = { it }
                ) { index ->
                    Box(
                        modifier = Modifier
                            .height(44.dp)
                            .padding(top = 24.dp, end = 4.dp),
                        contentAlignment = Alignment.TopEnd
                    ) {
                        if(index == 1) HasText(text = "총 ${state.styleList.size}개")
                    }
                }

                items(
                    items = state.styleList,
                    key = { it.id }
                ) { style ->
                    val isSelected by remember(state.selectedStyle) {
                        derivedStateOf {
                            state.selectedStyle?.id == style.id
                        }
                    }

                    StyleMainItem(
                        style = style,
                        isSelected = isSelected,
                        isMain = true,
                        onClick = { onAction.invoke(StyleScreenAction.ShowMoreStyle(it)) },
                        onLongClick = { onAction.invoke(StyleScreenAction.SelectStyle(it)) },
                        onEdit = { onAction.invoke(StyleScreenAction.ShowItemActivity(it)) },
                        onDelete = { onAction.invoke(StyleScreenAction.DeleteStyle(it)) }
                    )
                }
            }
        } else {
            StyleEmptyScreen()
        }
    }
}

@DevicePreviews
@Composable
fun EssentialScreenPreview() {
    StyleScreen(
        state = StyleScreenState(
            styleList = emptyList(),
            totalTagList = testTagList,
            selectedTagList = emptyList(),
            selectedStyle = null,
        ),
        onAction = {},
    )
}