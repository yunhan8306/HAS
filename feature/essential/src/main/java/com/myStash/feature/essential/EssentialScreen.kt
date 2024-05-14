package com.myStash.feature.essential

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import coil.compose.SubcomposeAsyncImage
import com.google.accompanist.navigation.animation.composable
import com.myStash.core.model.Item
import com.myStash.core.model.Tag
import com.myStash.design_system.animation.enterTransitionStart
import com.myStash.design_system.animation.exitTransitionStart
import com.myStash.design_system.util.ShimmerLoadingAnimation
import com.myStash.navigation.MainNavType
import org.orbitmvi.orbit.compose.collectAsState

fun NavGraphBuilder.essentialScreen(
    showItemActivity: (Item?) -> Unit
) {

    composable(
        route = MainNavType.ESSENTIAL.name,
        enterTransition = { enterTransitionStart },
        exitTransition = { exitTransitionStart }
    ) {
        EssentialRoute(
            showItemActivity = showItemActivity,
        )
    }
}

@Composable
fun EssentialRoute(
    viewModel: EssentialViewModel = hiltViewModel(),
    showItemActivity: (Item?) -> Unit,
) {

    val itemList = viewModel.collectAsState().value.itemList
    val tagTotalList = viewModel.collectAsState().value.tagTotalList

    EssentialScreen(
        itemList = itemList,
        tagTotalList = tagTotalList,
        showItemActivity = showItemActivity,
    )
}

@Composable
fun EssentialScreen(
    itemList: List<Item>,
    tagTotalList: List<Tag>,
    showItemActivity: (Item?) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Essential Screen",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp)
                .background(Color.Green),
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            items(itemList) { item ->
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