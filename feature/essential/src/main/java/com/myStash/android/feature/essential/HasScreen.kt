package com.myStash.android.feature.essential

import android.content.Intent
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text2.input.TextFieldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import coil.compose.SubcomposeAsyncImage
import com.google.accompanist.navigation.animation.composable
import com.myStash.android.common.util.CommonActivityResultContract
import com.myStash.android.core.model.Item
import com.myStash.android.core.model.Tag
import com.myStash.android.core.model.Type
import com.myStash.android.core.model.testManTypeTotalList
import com.myStash.android.core.model.testTagList
import com.myStash.android.design_system.animation.slideIn
import com.myStash.android.design_system.ui.component.dialog.HasConfirmDialog
import com.myStash.android.design_system.ui.DevicePreviews
import com.myStash.android.design_system.ui.component.SpacerLineBox
import com.myStash.android.design_system.ui.component.content.ContentHeaderSearchText
import com.myStash.android.design_system.ui.component.has.HasMainItem
import com.myStash.android.design_system.ui.component.tag.TagChipItem
import com.myStash.android.design_system.ui.component.tag.TagHasChipItem
import com.myStash.android.design_system.ui.component.tag.TagMoreChipItem
import com.myStash.android.design_system.ui.component.text.HasText
import com.myStash.android.design_system.util.ShimmerLoadingAnimation
import com.myStash.android.feature.item.ItemActivity
import com.myStash.android.feature.search.SearchScreen
import com.myStash.android.navigation.MainNavType
import kotlinx.coroutines.delay
import org.orbitmvi.orbit.compose.collectAsState

fun NavGraphBuilder.hasScreen() {
    composable(
        route = MainNavType.ESSENTIAL.name
    ) {
        HasRoute()
    }
}

@Composable
fun HasRoute(
    viewModel: HasViewModel = hiltViewModel(),
) {

    val itemList = viewModel.collectAsState().value.itemList
    val tagTotalList = viewModel.collectAsState().value.tagTotalList
    val selectTagList = viewModel.collectAsState().value.selectTagList
    val typeTotalList = viewModel.collectAsState().value.typeTotalList
    val selectedType = viewModel.collectAsState().value.selectedType
    val searchTextState by remember { mutableStateOf(viewModel.searchTextState) }

    val activity = LocalContext.current as ComponentActivity
    val itemActivityLauncher = rememberLauncherForActivityResult(
        contract = CommonActivityResultContract(),
        onResult = {}
    )

    var testSearchFlag by remember { mutableStateOf(false) }

    var testConfirm by remember { mutableStateOf(false) }

    HasScreen(
        searchTextState = searchTextState,
        typeTotalList = typeTotalList,
        selectedType = selectedType,
        onSelectType = viewModel::selectType,
        itemList = itemList,
        tagTotalList = tagTotalList,
        selectTagList = selectTagList,
        search = { testSearchFlag = true },
        selectTag = viewModel::selectTag,
        testItemAdd = viewModel::testItemAdd,
        testTagAdd = viewModel::testTagAdd,
        testConfirm = { testConfirm = true},
        deleteAllItem = viewModel::deleteAllItem,
        deleteAllTag = viewModel::deleteAllTag,
        showItemActivity = { item ->
            val intent = Intent(activity.apply { slideIn() }, ItemActivity::class.java)
                .putExtra("item", item)
            itemActivityLauncher.launch(intent)
        },
    )

    if(testSearchFlag) {
        SearchScreen(
            searchTextState = searchTextState,
            searchText = searchTextState.text.toString(),
            selectTagList = selectTagList,
            tagList = tagTotalList,
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
fun HasScreen(
    searchTextState: TextFieldState,
    typeTotalList: List<Type>,
    selectedType: Type,
    onSelectType: (Type) -> Unit,
    itemList: List<Item>,
    tagTotalList: List<Tag>,
    selectTagList: List<Tag>,
    search: () -> Unit,
    showItemActivity: (Item?) -> Unit,
    selectTag: (Tag) -> Unit,
    testItemAdd: () -> Unit,
    testTagAdd: () -> Unit,
    testConfirm: () -> Unit,
    deleteAllItem: () -> Unit,
    deleteAllTag: () -> Unit,
) {

    val tagScrollState = rememberLazyListState()
    var testTagToggle by remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()

    var flowToggle by remember { mutableStateOf(false) }

    LaunchedEffect(tagTotalList) {
        tagScrollState.scrollToItem(0)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        ContentHeaderSearchText(
            text = "원하는 태그를 검색해 보세요",
            onClick = search
        )
        LazyRow(
            modifier = Modifier
                .padding(top = 12.dp)
                .fillMaxWidth()
                .drawBehind {
                    drawLine(
                        color = Color(0xFFF1F1F1),
                        start = Offset(x = 0f, y = size.height),
                        end = Offset(x = size.width, y = size.height),
                        strokeWidth = 1.dp.toPx(),
                        cap = StrokeCap.Square
                    )
                }
        ) {
            items(
                items = typeTotalList,
                key = { type -> type.name}
            ) { type ->
                TypeItem(
                    name = type.name,
                    isSelected = selectedType.id == type.id,
                    onClick = { onSelectType.invoke(type) }
                )
            }
        }
        FlowRow(
            modifier = Modifier
                .heightIn(max = if(flowToggle) 200.dp else Dp.Unspecified)
                .fillMaxWidth()
                .padding(top = 18.dp, bottom = 8.dp, start = 16.dp, end = 16.dp)
        ) {
            testTagList.forEachIndexed { index, tag ->
                val isSelected by remember(selectTagList) {
                    derivedStateOf {
                        selectTagList.contains(tag)
                    }
                }
                if(index < 3 || flowToggle) {
                    TagChipItem(
                        name = tag.name,
                        isSelected = isSelected,
                        onClick = { selectTag.invoke(tag) }
                    )
                }
            }
            TagMoreChipItem(
                cnt = "${testTagList.size - 4}",
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
                items = itemList,
                key = { it.id!! }
            ) { has ->
                HasMainItem(
                    imagePath = has.imagePath,
                    tagList = has.getTagList(tagTotalList),
                    selectTagList = selectTagList,
                    onSelect = { showItemActivity.invoke(has) }
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

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomStart
    ) {
        Row {
            Text(
                modifier = Modifier
                    .height(40.dp)
                    .background(Color.Red)
                    .clickable { testTagAdd.invoke() },
                text = "test tag Add"
            )
            Text(
                modifier = Modifier
                    .height(40.dp)
                    .background(Color.Red)
                    .clickable { testItemAdd.invoke() },
                text = "test Item Add"
            )
            Text(
                modifier = Modifier
                    .height(40.dp)
                    .background(Color.Red)
                    .clickable { deleteAllTag.invoke() },
                text = "delete all Tag"
            )
            Text(
                modifier = Modifier
                    .height(40.dp)
                    .background(Color.Red)
                    .clickable { deleteAllItem.invoke() },
                text = "delete all Item"
            )
            Text(
                modifier = Modifier
                    .height(40.dp)
                    .background(Color.Red)
                    .clickable { testConfirm.invoke() },
                text = "confirm"
            )
        }
    }
}

@DevicePreviews
@Composable
fun EssentialScreenPreview() {
    HasScreen(
        searchTextState = TextFieldState(),
        typeTotalList = testManTypeTotalList,
        selectedType = Type(id = 0),
        onSelectType = {},
        itemList = emptyList(),
        tagTotalList = testTagList,
        selectTagList = emptyList(),
        search = {},
        showItemActivity = {},
        selectTag = {},
        testItemAdd = {},
        testTagAdd = {},
        testConfirm = {},
        deleteAllItem = {},
        deleteAllTag = {},
    )
}