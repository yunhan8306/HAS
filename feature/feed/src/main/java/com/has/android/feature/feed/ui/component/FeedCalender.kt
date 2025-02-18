package com.has.android.feature.feed.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import com.has.android.core.model.CalenderData
import com.has.android.core.model.setCalender
import com.has.android.design_system.ui.DevicePreviews
import com.has.android.design_system.util.ShimmerLoadingAnimation
import java.time.LocalDate

@Composable
fun FeedCalender(
    year: Int,
    month: Int,
    selectDate: LocalDate,
    calenderDataList: List<CalenderData>,
    onClickAgoCalender: () -> Unit,
    onClickNextCalender: () -> Unit,
    onClickDay: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .background(color = Color(0xFFFFFFFF), shape = RoundedCornerShape(size = 12.dp))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(20.dp)
                    .background(Color.Black)
                    .clickable { onClickAgoCalender.invoke() }
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "$year.$month",
                style = TextStyle(
                    fontSize = 16.sp,
                    lineHeight = 16.sp,
                    fontWeight = FontWeight(600),
                    color = Color(0xFF202020),
                )
            )
            Spacer(modifier = Modifier.weight(1f))
            Box(
                modifier = Modifier
                    .size(20.dp)
                    .background(Color.Black)
                    .clickable { onClickNextCalender.invoke() }
            )
        }

        LazyVerticalGrid(
            modifier = Modifier.fillMaxWidth(),
            columns = GridCells.Fixed(7)
        ) {
            items(calenderDataList) { calenderData ->
                when(calenderData) {
                    is CalenderData.DayOfWeek -> FeedCalenderDayOfWeekItem(dayOfWeek = calenderData.name)
                    is CalenderData.Spacer -> FeedCalenderSpacerItem()
                    is CalenderData.Day -> {
                        val isSelect by remember(selectDate, year, month) {
                            derivedStateOf {
                                year == selectDate.year && month == selectDate.monthValue && calenderData.day == selectDate.dayOfMonth.toString()
                            }
                        }

                        FeedCalenderDayItem(
                            day = calenderData.day,
                            isSelect = isSelect,
                            onClick = onClickDay
                        )
                    }
                    is CalenderData.RecodedDay -> FeedCalenderRecordDayItem(
                        day = calenderData.day,
                        imageUri = calenderData.imageUri,
                        onClick = onClickDay
                    )
                }
            }
        }
    }
}

@Composable
fun FeedCalenderDayOfWeekItem(
    dayOfWeek: String,
) {
    Box(
        modifier = Modifier.size(44.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = dayOfWeek,
            style = TextStyle(
                fontSize = 12.sp,
                lineHeight = 12.sp,
                fontWeight = FontWeight(500),
                color = Color(0xFF909090),
                textAlign = TextAlign.Center,
            )
        )
    }
}

@Composable
fun FeedCalenderSpacerItem() {
    Box(
        modifier = Modifier.size(44.dp)
    )
}

@Composable
fun FeedCalenderDayItem(
    day: String,
    isSelect: Boolean,
    onClick: (String) -> Unit,
) {
    Box(
        modifier = Modifier
            .size(44.dp)
            .clickable { onClick.invoke(day) },
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(RoundedCornerShape(18.dp))
                .background(if(isSelect) Color(0xFFE4F562) else Color.White),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = day,
                style = TextStyle(
                    fontSize = 12.sp,
                    lineHeight = 12.sp,
                    fontWeight = FontWeight(500),
                    color = Color(0xFF909090),
                    textAlign = TextAlign.Center,
                )
            )
        }
    }
}

@Composable
fun FeedCalenderRecordDayItem(
    day: String,
    imageUri: String,
    onClick: (String) -> Unit,
) {
    Box(
        modifier = Modifier
            .size(44.dp)
            .clickable { onClick.invoke(day) },
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(RoundedCornerShape(18.dp))
        ) {
            SubcomposeAsyncImage(
                model = imageUri,
                contentDescription = "feed image",
                modifier = Modifier.aspectRatio(1f),
                contentScale = ContentScale.Crop,
                loading = { ShimmerLoadingAnimation() },
                error = { ShimmerLoadingAnimation() }
            )
        }
        Text(
            text = day,
            style = TextStyle(
                fontSize = 12.sp,
                lineHeight = 12.sp,
                fontWeight = FontWeight(500),
                color = Color(0xFF909090),
                textAlign = TextAlign.Center,
            )
        )
    }
}

@DevicePreviews
@Composable
fun FeedCalenderPreview() {
    FeedCalender(
        year = LocalDate.now().year,
        month = LocalDate.now().monthValue,
        selectDate = LocalDate.now(),
        calenderDataList = setCalender(LocalDate.now().year, LocalDate.now().monthValue),
        onClickAgoCalender = {},
        onClickNextCalender = {},
        onClickDay = {},
    )
}



val dayOfWeekList = listOf("일", "월", "화", "수", "목", "금", "토")
val dayList = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31)
val spacerList = listOf(0, 0, 0)