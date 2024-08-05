package com.myStash.android.design_system.ui.component.calender

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontVariation.width
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import com.myStash.android.core.model.CalenderData
import com.myStash.android.core.model.setCalender
import com.myStash.android.design_system.ui.DevicePreviews
import com.myStash.android.design_system.ui.component.text.HasFontWeight
import com.myStash.android.design_system.ui.component.text.HasText
import com.myStash.android.design_system.util.ShimmerLoadingAnimation
import java.time.LocalDate

@Composable
fun HasCalender(
    modifier: Modifier = Modifier,
    year: Int,
    month: Int,
    selectDate: LocalDate,
    calenderDataList: List<CalenderData>,
    onClickAgoCalender: () -> Unit,
    onClickNextCalender: () -> Unit,
    onClickDay: (LocalDate) -> Unit,
) {

    var isShowDatePicker by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp)
                .padding(horizontal = 16.dp)
        ) {
            Box(modifier = Modifier.height(22.dp))
            Row(
                modifier = Modifier.clickable { isShowDatePicker = !isShowDatePicker },
                verticalAlignment = Alignment.CenterVertically
            ) {
                HasText(
                    text = "$year.$month",
                    fontSize = 16.dp,
                    fontWeight = HasFontWeight.Bold
                )
                Box(modifier = Modifier
                    .size(12.dp)
                    .background(Color.Blue))
            }
            Box(modifier = Modifier.height(14.dp))
            LazyVerticalGrid(
                modifier = Modifier.fillMaxWidth(),
                columns = GridCells.Fixed(7),
                userScrollEnabled = false
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
                                onClick = { onClickDay.invoke(LocalDate.of(year, month, calenderData.day.toInt())) }
                            )
                        }
                        is CalenderData.RecodedDay -> {
                            val isSelect by remember(selectDate, year, month) {
                                derivedStateOf {
                                    year == selectDate.year && month == selectDate.monthValue && calenderData.day == selectDate.dayOfMonth.toString()
                                }
                            }

                            FeedCalenderRecordDayItem(
                                isSelect = isSelect,
                                day = calenderData.day,
                                imageUri = calenderData.imageUri,
                                onClick = { onClickDay.invoke(LocalDate.of(year, month, calenderData.day.toInt())) }
                            )
                        }
                    }
                }
            }
        }

        if(isShowDatePicker) {
            // 피커 작업 필요
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
            BackHandler { isShowDatePicker = false }
        }
    }
}

@Composable
fun FeedCalenderDayOfWeekItem(
    dayOfWeek: String,
) {
    Box(
        modifier = Modifier
            .width(44.dp)
            .height(36.dp),
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
        modifier = Modifier
            .width(44.dp)
            .height(36.dp)
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
            .width(44.dp)
            .height(36.dp)
            .clickable { onClick.invoke(day) },
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(RoundedCornerShape(18.dp))
                .background(if (isSelect) Color(0xFFE4F562) else Color.White),
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
    isSelect: Boolean,
    day: String,
    imageUri: String,
    onClick: (String) -> Unit,
) {
    Box(
        modifier = Modifier
            .width(44.dp)
            .height(36.dp)
            .clickable { onClick.invoke(day) },
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(RoundedCornerShape(18.dp))
                .border(width = if(isSelect) 1.dp else (-1).dp, color = Color(0xFF202020), shape = RoundedCornerShape(size = 18.dp))
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
        HasText(
            text = day,
            fontSize = 12.dp,
            color = if(isSelect) Color(0xFF202020) else Color(0xFFFFFFFF)
        )
    }
}


@DevicePreviews
@Composable
fun HasCalenderPreview() {
    HasCalender(
        year = 2024,
        month = 8,
        selectDate = LocalDate.now(),
        calenderDataList = setCalender(2024, 8),
        onClickAgoCalender = {},
        onClickNextCalender = {},
        onClickDay = {}
    )
}