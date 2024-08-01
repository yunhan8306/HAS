package com.myStash.android.feature.feed

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable
import com.myStash.android.core.model.CalenderData
import com.myStash.android.navigation.MainNavType
import org.orbitmvi.orbit.compose.collectAsState
import java.time.LocalDate

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

    val currentDate = viewModel.collectAsState().value.calenderDate
    val calenderDataList = viewModel.collectAsState().value.calenderDataList
    val selectDate = viewModel.collectAsState().value.selectDate

    FeedScreen(
        year = currentDate.year,
        month = currentDate.monthValue,
        selectDate = selectDate,
        calenderDataList = calenderDataList,
        onClickAgoCalender = viewModel::onClickAgoCalender,
        onClickNextCalender = viewModel::onClickNextCalender,
        onClickDay = viewModel::onClickDay,
    )
}

@Composable
fun FeedScreen(
    year: Int,
    month: Int,
    selectDate: LocalDate,
    calenderDataList: List<CalenderData>,
    onClickAgoCalender: () -> Unit,
    onClickNextCalender: () -> Unit,
    onClickDay: (String) -> Unit,
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
                year = year,
                month = month,
                selectDate = selectDate,
                calenderDataList = calenderDataList,
                onClickAgoCalender = onClickAgoCalender,
                onClickNextCalender = onClickNextCalender,
                onClickDay = onClickDay,
            )
        }
    }
}