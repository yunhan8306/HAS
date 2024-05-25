package com.myStash.android.feature.essential

import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text2.input.TextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
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
import com.myStash.android.core.model.testTypeTotalList
import com.myStash.android.design_system.animation.enterTransitionStart
import com.myStash.android.design_system.animation.exitTransitionStart
import com.myStash.android.design_system.animation.slideIn
import com.myStash.android.design_system.ui.DevicePreviews
import com.myStash.android.design_system.ui.SearchTextField
import com.myStash.android.design_system.util.ShimmerLoadingAnimation
import com.myStash.android.feature.item.ItemActivity
import com.myStash.android.navigation.MainNavType
import org.orbitmvi.orbit.compose.collectAsState

fun NavGraphBuilder.essentialScreen() {
    composable(
        route = MainNavType.ESSENTIAL.name,
        enterTransition = { enterTransitionStart },
        exitTransition = { exitTransitionStart }
    ) {
        EssentialRoute()
    }
}

@Composable
fun EssentialRoute(
    viewModel: EssentialViewModel = hiltViewModel(),
) {

    val itemList = viewModel.collectAsState().value.itemList
    val tagTotalList = viewModel.collectAsState().value.tagTotalList
    val selectTagList = viewModel.collectAsState().value.selectTagList
    val typeTotalList = viewModel.typeTotalList
    val selectedType = viewModel.collectAsState().value.selectedType
    val searchTextState by remember { mutableStateOf(viewModel.searchTextState) }

    val activity = LocalContext.current as ComponentActivity
    val itemActivityLauncher = rememberLauncherForActivityResult(
        contract = CommonActivityResultContract(),
        onResult = {}
    )

    EssentialScreen(
        searchTextState = searchTextState,
        typeTotalList = typeTotalList,
        selectedType = selectedType,
        onSelectType = viewModel::selectType,
        itemList = itemList,
        tagTotalList = tagTotalList,
        selectTagList = selectTagList,
        selectTag = viewModel::selectTag,
        testItemAdd = viewModel::testItemAdd,
        testTagAdd = viewModel::testTagAdd,
        deleteAllItem = viewModel::deleteAllItem,
        deleteAllTag = viewModel::deleteAllTag,
        showItemActivity = { item ->
            val intent = Intent(activity.apply { slideIn() }, ItemActivity::class.java)
                .putExtra("item", item)
            itemActivityLauncher.launch(intent)
        },
    )
}

@Composable
fun EssentialScreen(
    searchTextState: TextFieldState,
    typeTotalList: List<Type>,
    selectedType: Type,
    onSelectType: (Type) -> Unit,
    itemList: List<Item>,
    tagTotalList: List<Tag>,
    selectTagList: List<Tag>,
    showItemActivity: (Item?) -> Unit,
    selectTag: (Tag) -> Unit,
    testItemAdd: () -> Unit,
    testTagAdd: () -> Unit,
    deleteAllItem: () -> Unit,
    deleteAllTag: () -> Unit,
) {

    val tagScrollState = rememberLazyListState()
    var testTagToggle by remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()
    var searchPositionY by remember { mutableFloatStateOf(0f) }

    LaunchedEffect(tagTotalList) {
        tagScrollState.scrollToItem(0)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .zIndex(1f)
    ) {
        if(scrollState.value - searchPositionY > 0) {
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                SearchTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    textState = searchTextState,
                )
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "FSSE",
            modifier = Modifier
                .fillMaxWidth()
                .height(16.dp),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(Color.White)
            .onGloballyPositioned { coordinates ->
                if (searchPositionY.toInt() == 0) {
                    searchPositionY = coordinates.positionInRoot().y
                }
            },
            contentAlignment = Alignment.Center
        ) {
            SearchTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                textState = searchTextState,
            )
        }
        Spacer(modifier = Modifier.height(18.dp))
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .height(34.dp)
                .drawBehind {
                    drawLine(
                        color = Color.Gray,
                        start = Offset(x = 0f, y = size.height),
                        end = Offset(x = size.width, y = size.height),
                        strokeWidth = 1.dp.toPx(),
                        cap = StrokeCap.Square
                    )
                }
            ,
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
        Spacer(modifier = Modifier.height(15.dp))
        Text(
            text = "Total Tag",
            modifier = Modifier
                .fillMaxWidth()
                .clickable { testTagToggle = !testTagToggle },
            textAlign = TextAlign.Center
        )
        LazyColumn(
            modifier = Modifier.height(if(testTagToggle) 50.dp else 200.dp)
        ) {
            items(10) {
                Box(
                    modifier = Modifier
                        .size(20.dp)
                        .background(if (it % 2 == 1) Color.Blue else Color.Black)
                )
            }
        }
        Spacer(modifier = Modifier.height(15.dp))
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp)
                .background(Color.Blue),
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            state = tagScrollState
        ) {
            items(
                items = tagTotalList,
                key = { it.name }
            ) { tag ->

                val isSelected by remember(selectTagList) {
                    derivedStateOf {
                        selectTagList.contains(tag)
                    }
                }

                EssentialTagItem(
                    name = tag.name,
                    isSelected = isSelected,
                    onClick = {
                        selectTag.invoke(tag)
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(15.dp))
        Text(
            text = "Select Tag",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(15.dp))

        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp)
                .background(Color.Blue),
            horizontalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            items(
                items = selectTagList,
                key = { it.name }
            ) { tag ->

                EssentialTagItem(
                    name = tag.name,
                    isSelected = false,
                    onClick = {}
                )
            }
        }


        Spacer(modifier = Modifier.height(15.dp))
        Text(
            text = "Grid",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(15.dp))
        LazyVerticalGrid(
            modifier = Modifier
//                .height(600.dp)
                .heightIn(max = 4000.dp)
                .fillMaxWidth()
                .padding(horizontal = 30.dp)
                .background(Color.Green)
            ,
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            items(
                items = itemList,
                key = { it.id!! }
            ) { item ->
                EssentialItem(
                    itemUri = item.imagePath,
                    tagList = item.getTagList(tagTotalList),
                    onClick = { showItemActivity.invoke(item) }
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
        }
    }
}

@Composable
fun EssentialItem(
    itemUri: String?,
    tagList: List<Tag>,
    onClick: () -> Unit = {},
) {

    Column(
        modifier = Modifier
            .heightIn(min = 150.dp)
            .background(Color.Gray)
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick.invoke() },
    ) {
        SubcomposeAsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .clip(RoundedCornerShape(12.dp)),
            loading = { ShimmerLoadingAnimation() },
            error = { ShimmerLoadingAnimation() },
            contentScale = ContentScale.Crop,
            model = itemUri, // 엑박 이미지 필요?
            contentDescription = "essential item"
        )
        Spacer(modifier = Modifier.height(20.dp))
        LazyRow {
            items(tagList) {
                Text(
                    text = it.name,
                    modifier = Modifier.padding(10.dp)
                )
            }
        }
    }
}

@Composable
fun EssentialTagItem(
    name: String,
    isSelected: Boolean,
    onClick: () -> Unit = {},
) {
    Box(
        modifier = Modifier
            .height(30.dp)
            .width(60.dp)
            .background(if (isSelected) Color.White else Color.Yellow)
            .clickable { onClick.invoke() },
        contentAlignment = Alignment.Center
    ) {
        Text(text = name)
    }
}

@DevicePreviews
@Composable
fun EssentialScreenPreview() {
    EssentialScreen(
        searchTextState = TextFieldState(),
        typeTotalList = testTypeTotalList,
        selectedType = Type(id = 0),
        onSelectType = {},
        itemList = emptyList(),
        tagTotalList = emptyList(),
        selectTagList = emptyList(),
        showItemActivity = {},
        selectTag = {},
        testItemAdd = {},
        testTagAdd = {},
        deleteAllItem = {},
        deleteAllTag = {},
    )
}