package com.myStash.android.feature.style

import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import coil.compose.SubcomposeAsyncImage
import com.google.accompanist.navigation.animation.composable
import com.myStash.android.common.util.CommonActivityResultContract
import com.myStash.android.core.model.Has
import com.myStash.android.core.model.StyleScreenModel
import com.myStash.android.core.model.Tag
import com.myStash.android.core.model.testTagList
import com.myStash.android.design_system.animation.slideIn
import com.myStash.android.design_system.ui.DevicePreviews
import com.myStash.android.design_system.ui.component.SpacerLineBox
import com.myStash.android.design_system.ui.component.content.ContentHeaderSearchText
import com.myStash.android.design_system.ui.component.dialog.HasConfirmDialog
import com.myStash.android.design_system.ui.component.tag.TagChipItem
import com.myStash.android.design_system.ui.component.tag.TagMoreChipItem
import com.myStash.android.design_system.util.ShimmerLoadingAnimation
import com.myStash.android.feature.item.ItemActivity
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
    viewModel: StyleViewModel = hiltViewModel(),
) {

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

    var testSearchFlag by remember { mutableStateOf(false) }
    var testConfirm by remember { mutableStateOf(false) }

    StyleScreen(
        totalTagList = totalTagList,
        selectedTagList = selectedTagList,
        styleList = styleList,
        selectedStyle = selectedStyle,
        onSearch = { testSearchFlag = true },
        onSelectTag = viewModel::selectTag,
        onSelectStyle = viewModel::selectStyle,
        onShowStyle = {},
        showItemActivity = { has ->
            val intent = Intent(activity.apply { slideIn() }, ItemActivity::class.java)
                .putExtra("has", has)
            itemActivityLauncher.launch(intent)
        },
    )

    if(testSearchFlag) {
        SearchScreen(
            searchTextState = searchTextState,
            searchText = searchTextState.text.toString(),
            selectTagList = selectedTagList,
            tagList = totalTagList,
            select = viewModel::selectTag,
            onBack = { testSearchFlag = false },
        )
    }

    HasConfirmDialog(
        isShow = testConfirm,
        title = "title",
        content = "content",
        confirmText = "confirm",
        dismissText = "닫기",
        onConfirm = { testConfirm = false },
        onDismiss = { testConfirm = false }
    )
}

@Composable
fun StyleScreen(
    totalTagList: List<Tag>,
    selectedTagList: List<Tag>,
    styleList: List<StyleScreenModel>,
    selectedStyle: StyleScreenModel?,
    onSearch: () -> Unit,
    onSelectTag: (Tag) -> Unit,
    onSelectStyle: (StyleScreenModel) -> Unit,
    onShowStyle: (StyleScreenModel) -> Unit,
    showItemActivity: (Has?) -> Unit,
) {

    val tagScrollState = rememberScrollState()
    var flowToggle by remember { mutableStateOf(false) }

    LaunchedEffect(totalTagList) {
        tagScrollState.scrollTo(0)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        ContentHeaderSearchText(
            text = "원하는 태그를 검색해 보세요",
            onClick = onSearch
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
                        onClick = { onSelectTag.invoke(tag) }
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
                .padding(top = 24.dp)
                .weight(1f)
                .fillMaxWidth(),
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
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
                    onClick = onShowStyle,
                    onLongClick = onSelectStyle
                )
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomEnd
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(Color.Red)
                .clickable { showItemActivity.invoke(null) }
        )
    }
}

@Composable
fun StyleMainItem(
    style: StyleScreenModel,
    isSelected: Boolean,
    onClick: (StyleScreenModel) -> Unit,
    onLongClick: (StyleScreenModel) -> Unit
) {
    Box(
        modifier = Modifier
            .border(width = if(isSelected) 2.dp else 1.dp, color = if(isSelected) Color(0xFF716DF6) else Color(0xFFE1E1E1), shape = RoundedCornerShape(size = 12.dp))
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

@DevicePreviews
@Composable
fun EssentialScreenPreview() {
    StyleScreen(
        styleList = emptyList(),
        totalTagList = testTagList,
        selectedTagList = emptyList(),
        selectedStyle = null,
        onSearch = {},
        showItemActivity = {},
        onSelectTag = {},
        onShowStyle = {},
        onSelectStyle = {}
    )
}