package com.myStash.android.feature.style

import android.content.Intent
import androidx.activity.ComponentActivity
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable
import com.myStash.android.common.compose.activityViewModel
import com.myStash.android.common.util.CommonActivityResultContract
import com.myStash.android.core.model.StyleScreenModel
import com.myStash.android.core.model.Tag
import com.myStash.android.core.model.testTagList
import com.myStash.android.design_system.animation.slideIn
import com.myStash.android.design_system.ui.DevicePreviews
import com.myStash.android.design_system.ui.component.SpacerLineBox
import com.myStash.android.design_system.ui.component.content.ContentHeaderSearchText
import com.myStash.android.design_system.ui.component.style.StyleMainItem
import com.myStash.android.design_system.ui.component.tag.TagChipItem
import com.myStash.android.design_system.ui.component.tag.TagMoreChipItem
import com.myStash.android.design_system.ui.component.text.HasText
import com.myStash.android.feature.item.ItemActivity
import com.myStash.android.feature.item.item.ItemTab
import com.myStash.android.feature.search.SearchScreen
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
    val totalTagList = viewModel.collectAsState().value.totalTagList
    val selectedTagList = viewModel.collectAsState().value.selectedTagList
    val styleList = viewModel.collectAsState().value.styleList
    val selectedStyle = viewModel.collectAsState().value.selectedStyle

    val searchTextState by remember { mutableStateOf(viewModel.searchTextState) }

    val activity = LocalContext.current as ComponentActivity
    val itemActivityLauncher = rememberLauncherForActivityResult(
        contract = CommonActivityResultContract(),
        onResult = {}
    )

    var isShowSearch by remember { mutableStateOf(false) }
    var showMoreStyle: StyleScreenModel? by remember { mutableStateOf(null) }

    StyleScreen(
        totalTagList = totalTagList,
        selectedTagList = selectedTagList,
        styleList = styleList,
        selectedStyle = selectedStyle,
        onAction = { action ->
            when(action) {
                is StyleScreenAction.ShowSearch -> isShowSearch = true
                is StyleScreenAction.ShowMoreStyle -> showMoreStyle = action.style
                is StyleScreenAction.ShowItemActivity -> {
                    val intent = Intent(activity, ItemActivity::class.java)
                        .putExtra("tab", ItemTab.STYLE.name)
                        .putExtra("style", action.style.hasList.map { it.id }.toTypedArray())
                    itemActivityLauncher.launch(intent)
                    activity.slideIn()
                }
                else -> viewModel.onAction(action)
            }
        }
    )

    if(isShowSearch) {
        SearchScreen(
            searchTextState = searchTextState,
            totalTagList = state.totalTagList,
            selectTagList = state.selectedTagList,
            searchTagList = state.searchTagList,
            selectedHasCnt = 0,
            onSelect = viewModel::selectTag,
            onConfirm = { isShowSearch = false },
            onDelete = viewModel::deleteSearchText,
            onBack = { isShowSearch = false },
        )
    }

    StyleMoreDialog(
        styleScreenModel = showMoreStyle,
        typeTotalList = state.totalTypeList,
        tagTotalList = state.totalTagList,
        onDismiss = { showMoreStyle = null },
    )
}

@Composable
fun StyleScreen(
    totalTagList: List<Tag>,
    selectedTagList: List<Tag>,
    styleList: List<StyleScreenModel>,
    selectedStyle: StyleScreenModel?,
    onAction: (StyleScreenAction) -> Unit,
) {

    val tagScrollState = rememberScrollState()
    var flowToggle by remember { mutableStateOf(false) }

    LaunchedEffect(totalTagList) {
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
                .heightIn(max = if (flowToggle) 200.dp else Dp.Unspecified)
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 8.dp, start = 16.dp, end = 16.dp)
                .verticalScroll(tagScrollState)
        ) {
            totalTagList.forEachIndexed { index, tag ->
                val isSelected by remember(selectedTagList) {
                    derivedStateOf {
                        tag.checkSelected(selectedTagList)
                    }
                }
                if(index < 3 || flowToggle) {
                    TagChipItem(
                        name = tag.name,
                        isSelected = isSelected,
                        onClick = { onAction.invoke(StyleScreenAction.SelectTag(tag)) }
                    )
                }
            }
            TagMoreChipItem(
                cnt = "${totalTagList.size - 4}",
                isFold = !flowToggle,
                onClick = { flowToggle = !flowToggle }
            )
        }
        SpacerLineBox()
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
                    if(index == 1) HasText(text = "총 ${styleList.size}개")
                }
            }

            items(
                items = styleList,
                key = { it.id }
            ) { style ->
                val isSelected by remember(selectedStyle) {
                    derivedStateOf {
                        selectedStyle?.id == style.id
                    }
                }

                StyleMainItem(
                    style = style,
                    isSelected = isSelected,
                    onClick = { onAction.invoke(StyleScreenAction.ShowMoreStyle(style)) },
                    onLongClick = { onAction.invoke(StyleScreenAction.SelectStyle(style)) }
                )
            }
        }
    }
}

@DevicePreviews
@Composable
fun EssentialScreenPreview() {
    StyleScreen(
        styleList = emptyList(),
        totalTagList = testTagList,
        selectedTagList = emptyList(),
        selectedStyle = null,
        onAction = {},
    )
}