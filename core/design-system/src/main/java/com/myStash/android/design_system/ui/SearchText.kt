package com.myStash.android.design_system.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text2.input.TextFieldState
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
fun SearchText(
    modifier: Modifier = Modifier,
    textState: TextFieldState,
    hint: String = "원하는 태그를 검색해 보세요",
    onClick: () -> Unit
) {
    Box(modifier = modifier
        .height(60.dp)
        .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(Color(0xFFF5F5F5))
                .clickable { onClick.invoke() },
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                modifier = Modifier.padding(start = 5.dp),
                text = if(textState.text.isEmpty()) hint else textState.text.toString(),
                color = if(textState.text.isEmpty()) Color(0xFFA4A4A4) else Color.Black,
                fontSize = 17.sp,
            )
        }
    }
}