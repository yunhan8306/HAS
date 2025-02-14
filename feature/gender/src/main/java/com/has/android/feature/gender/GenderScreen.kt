package com.has.android.feature.gender

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun GenderScreen(
    selectGenderType: GenderType,
    selectGender: (GenderType) -> Unit,
    saveGender: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = "성별을 선택해 주세요",
            fontSize = 20.sp,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(30.dp))
        Row {
            GenderItem(
                name = "여성",
                isSelected = selectGenderType == GenderType.WOMAN,
                onClick = { selectGender.invoke(GenderType.WOMAN) }
            )
            Spacer(modifier = Modifier.width(16.dp))
            GenderItem(
                name = "남성",
                isSelected = selectGenderType == GenderType.MAN,
                onClick = { selectGender.invoke(GenderType.MAN) }
            )
        }
        Box(
            modifier = Modifier
                .size(50.dp)
                .clickable { saveGender.invoke() },
            contentAlignment = Alignment.Center
        ) {
            Text("save")
        }
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
fun GenderItem(
    name: String,
    imageRes: Int? = null,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .height(172.dp)
            .width(132.dp)
            .clip(RoundedCornerShape(12.dp))
            .border(
                width = 2.dp,
                color = if (isSelected) Color(0xFF716DF6) else Color(0xFFE1E1E1),
                shape = RoundedCornerShape(12.dp)
            )
            .padding(10.dp)
            .clickable { onClick.invoke() }
    ) {
        Text(text = name)
        Spacer(modifier = Modifier.weight(1f))
        Box(
            Modifier.fillMaxWidth(),
            contentAlignment = Alignment.BottomEnd
        ) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .background(if (isSelected) Color(0xFF716DF6) else Color(0xFFE1E1E1)),
            ) {

            }
        }
    }
}