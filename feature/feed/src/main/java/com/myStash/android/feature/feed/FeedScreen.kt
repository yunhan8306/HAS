package com.myStash.android.feature.feed

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable
import com.myStash.android.navigation.MainNavType
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

    val calenderDataList = viewModel.collectAsState().value.calenderDataList

    FeedScreen(
        calenderDataList = calenderDataList
    )
}

@Composable
fun FeedScreen(
    calenderDataList: List<CalenderData>
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        FeedHeader()
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(Color(0xFFF5F5F5))
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            FeedCalender(
                calenderDataList = calenderDataList
            )
        }
    }
}