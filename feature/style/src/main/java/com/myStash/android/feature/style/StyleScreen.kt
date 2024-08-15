package com.myStash.android.feature.style

import androidx.activity.compose.BackHandler
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable
import com.myStash.android.common.compose.activityViewModel
import com.myStash.android.core.model.StyleScreenModel
import com.myStash.android.core.model.testTagList
import com.myStash.android.design_system.ui.DevicePreviews
import com.myStash.android.design_system.ui.component.SpacerLineBox
import com.myStash.android.design_system.ui.component.content.ContentHeaderSearchText
import com.myStash.android.design_system.ui.component.style.StyleMainItem
import com.myStash.android.design_system.ui.component.tag.TagChipItem
import com.myStash.android.design_system.ui.component.tag.TagMoreChipItem
import com.myStash.android.design_system.ui.component.text.HasText
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

    val searchTextState by remember { mutableStateOf(viewModel.searchTextState) }

    var isShowSearch by remember { mutableStateOf(false) }
    var showMoreStyle: StyleScreenModel? by remember { mutableStateOf(null) }

    StyleScreen(
        state = state,
        onAction = { action ->
            when(action) {
                is StyleScreenAction.ShowSearch -> isShowSearch = true
                is StyleScreenAction.ShowMoreStyle -> showMoreStyle = action.style
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
                cnt = "${state.totalTagList.size - 4}",
                isFold = state.isFoldTag,
                onClick = { onAction.invoke(StyleScreenAction.TagToggle) }
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
        state = StyleScreenState(
            styleList = emptyList(),
            totalTagList = testTagList,
            selectedTagList = emptyList(),
            selectedStyle = null,
        ),
        onAction = {},
    )
}