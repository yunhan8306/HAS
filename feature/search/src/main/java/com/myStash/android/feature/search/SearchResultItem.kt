package com.myStash.android.feature.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SearchResultItem(
    name: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .background(if (isSelected) Color(0xFFFCFFE7) else Color(0xFFFFFFFF))
                .clickable { onClick.invoke() },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.padding(start = 12.dp),
                text = name,
                fontSize = 14.sp,
            )
            Spacer(modifier = Modifier.width(6.dp))
            if(isSelected) {
                Box(
                    modifier = Modifier
                        .size(14.dp)
                        .background(Color(0xFFBFD320))
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "cnt"
            )
        }
        Box(
            Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(color = Color(0xFFF1F1F1))
        ) {

        }
    }
}