package com.myStash.android.feature.essential

import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable
import com.myStash.android.common.compose.activityViewModel
import com.myStash.android.common.util.CommonActivityResultContract
import com.myStash.android.core.model.getTotalType
import com.myStash.android.core.model.testManTypeTotalList
import com.myStash.android.core.model.testTagList
import com.myStash.android.design_system.animation.fadeIn
import com.myStash.android.design_system.animation.slideIn
import com.myStash.android.design_system.ui.DevicePreviews
import com.myStash.android.design_system.ui.color.ColorFamilyGray200AndGray600
import com.myStash.android.design_system.ui.color.ColorFamilyGray500AndGray900
import com.myStash.android.design_system.ui.component.SpacerLineBox
import com.myStash.android.design_system.ui.component.content.ContentHeaderSearchText
import com.myStash.android.design_system.ui.component.dialog.HasConfirmDialog
import com.myStash.android.design_system.ui.component.has.HasMainItem
import com.myStash.android.design_system.ui.component.tag.TagChipItem
import com.myStash.android.design_system.ui.component.tag.TagMoreChipItem
import com.myStash.android.design_system.ui.component.text.HasText
import com.myStash.android.feature.item.ItemActivity
import com.myStash.android.feature.item.item.ItemTab
import com.myStash.android.feature.search.SearchActivity
import com.myStash.android.feature.search.SearchConstants
import com.myStash.android.navigation.MainNavType
import org.orbitmvi.orbit.compose.collectAsState

fun NavGraphBuilder.hasScreen() {
    composable(
        route = MainNavType.HAS.name
    ) {
        HasRoute()
    }
}

@Composable
fun HasRoute(
    viewModel: HasViewModel = activityViewModel()
) {

    val state by viewModel.collectAsState()

    val activity = LocalContext.current as ComponentActivity
    val itemActivityLauncher = rememberLauncherForActivityResult(
        contract = CommonActivityResultContract(),
        onResult = {}
    )

    val searchActivityLauncher = rememberLauncherForActivityResult(
        contract = CommonActivityResultContract(),
        onResult = { intent ->
            intent?.getLongArrayExtra(SearchConstants.SELECTED_TAG_LIST)?.let {
                viewModel.onAction(HasScreenAction.SetTagList(it.toList()))
            }
        }
    )

    var testConfirm by remember { mutableStateOf(false) }

    HasScreen(
        state = state,
        onAction = { action ->
             when(action) {
                 is HasScreenAction.ShowSearch -> {
                     val intent = Intent(activity, SearchActivity::class.java)
                         .putExtra(SearchConstants.TYPE, SearchConstants.HAS)
                         .putExtra(SearchConstants.SELECTED_TAG_LIST, state.selectedTagList.map { it.id }.toTypedArray())

                     searchActivityLauncher.launch(intent)
                     activity.fadeIn()
                 }
                 is HasScreenAction.ShowItemActivity -> {
                     val intent = Intent(activity, ItemActivity::class.java)
                         .putExtra("tab", ItemTab.HAS.name)
                         .putExtra("has", action.has)
                     itemActivityLauncher.launch(intent)
                     activity.slideIn()
                 }

                 else -> viewModel.onAction(action)
             }
        }
    )

    HasConfirmDialog(
        isShow = testConfirm,
        title = "title",
        content = "content",
        confirmText = "confirm",
        dismissText = "닫기",
        onConfirm = { testConfirm = false },
        onDismiss = { testConfirm = false }
    )

    if(state.selectedHasList.isNotEmpty()) {
        BackHandler {
            viewModel.onAction(HasScreenAction.ResetSelectHas)
        }
    }
}

@Composable
fun HasScreen(
    state: HasScreenState,
    onAction: (HasScreenAction) -> Unit,
) {

    val tagScrollState = rememberScrollState()
    val drawLineColor = ColorFamilyGray200AndGray600

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
            onClick = { onAction.invoke(HasScreenAction.ShowSearch) }
        )
        LazyRow(
            modifier = Modifier
                .height(44.dp)
                .fillMaxWidth()
                .padding(top = 12.dp)
                .drawBehind {
                    drawLine(
                        color = drawLineColor,
                        start = Offset(x = 0f, y = size.height),
                        end = Offset(x = size.width, y = size.height),
                        strokeWidth = 1.dp.toPx(),
                        cap = StrokeCap.Square
                    )
                }
        ) {
            items(
                items = state.totalTypeList,
                key = { type -> type.name}
            ) { type ->
                TypeItem(
                    name = type.name,
                    isSelected = state.selectedType.id == type.id,
                    onClick = { onAction.invoke(HasScreenAction.SelectType(type)) }
                )
            }
        }
        FlowRow(
            modifier = Modifier
                .heightIn(max = if (state.isFoldTag) 200.dp else Dp.Unspecified)
                .fillMaxWidth()
                .padding(top = 18.dp, bottom = 8.dp, start = 16.dp, end = 16.dp)
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
                        onClick = { onAction.invoke(HasScreenAction.SelectTag(tag)) }
                    )
                }
            }
            if(state.totalTagList.size > 3) {
                TagMoreChipItem(
                    cnt = "${state.totalTagList.size - 4}",
                    isFold = state.isFoldTag,
                    onClick = { onAction.invoke(HasScreenAction.TagToggle) }
                )
            }
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
                    if(index == 1) {
                        HasText(
                            text = "총 ${state.hasList.size}개",
                            color = ColorFamilyGray500AndGray900
                        )
                    }
                }
            }
            items(
                items = state.hasList,
                key = { it.id!! }
            ) { has ->

                val selectedNumber by remember(state.selectedHasList) {
                    derivedStateOf {
                        state.selectedHasList.indexOf(has).takeIf { it != -1 }?.let { it + 1 }
                    }
                }

                val isSelectedMode by remember(state.selectedHasList) {
                    derivedStateOf {
                        state.selectedHasList.isNotEmpty()
                    }
                }

                HasMainItem(
                    has = has,
                    isSelectedMode = isSelectedMode,
                    selectedNumber = selectedNumber,
                    tagList = has.getTagList(state.totalTagList),
                    selectTagList = state.selectedTagList,
                    onSelectHas = { onAction.invoke(HasScreenAction.SelectHas(has)) },
                    onEditHas = { onAction.invoke(HasScreenAction.ShowItemActivity(has)) },
                    onDeleteHas = {},
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
                .clickable { onAction.invoke(HasScreenAction.ShowItemActivity(null)) }
        )
    }
}

@DevicePreviews
@Composable
fun HasScreenPreview() {
    HasScreen(
        state = HasScreenState(
            totalTypeList = testManTypeTotalList,
            selectedType = getTotalType(),
            totalTagList = testTagList,
        ),
        onAction = {},
    )
}