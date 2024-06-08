package com.myStash.android.feature.feed

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.myStash.android.design_system.ui.DevicePreviews

@Composable
fun FeedCalender(

) {
    Column(
        modifier = Modifier
            .width(328.dp)
            .height(336.dp)
            .padding(horizontal = 16.dp)
            .background(color = Color(0xFFFFFFFF), shape = RoundedCornerShape(size = 12.dp))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "2024.05",
                style = TextStyle(
                    fontSize = 16.sp,
                    lineHeight = 16.sp,
                    fontWeight = FontWeight(600),
                    color = Color(0xFF202020),
                )
            )
            Spacer(modifier = Modifier.weight(1f))
        }

        LazyVerticalGrid(
            modifier = Modifier.fillMaxWidth(),
            columns = GridCells.Fixed(7),
            horizontalArrangement = Arrangement.spacedBy(1.dp),
            verticalArrangement = Arrangement.spacedBy(1.dp)
        ) {
            items(dayOfWeekList) {
                FeedCalenderDayOfWeekItem(it)
            }
            items(spacerList) {
                FeedCalenderSpacerItem()
            }
            items(dayList) {
                FeedCalenderDayItem(it)
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
    day: Int,
) {
    Box(
        modifier = Modifier.size(44.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = day.toString(),
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
    FeedCalender()
}



val dayOfWeekList = listOf("일", "월", "화", "수", "목", "금", "토")
val dayList = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31)
val spacerList = listOf(0, 0, 0)