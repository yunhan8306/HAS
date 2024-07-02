package com.myStash.android.design_system.ui.tag

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.myStash.android.common.resource.R
import com.myStash.android.common.util.highlightTextBuilder
import com.myStash.android.design_system.ui.DevicePreviews
import com.myStash.android.design_system.ui.HasFontWeight
import com.myStash.android.design_system.ui.HasText

@Composable
fun TagSearchItem(
    searchText: String = "",
    name: String,
    cnt: String = "",
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .background(if (isSelected) Color(0xFFFCFFE7) else Color(0xFFFFFFFF))
            .padding(horizontal = 12.dp)
            .drawBehind {
                drawLine(
                    color = Color(0xFFF1F1F1),
                    start = Offset(x = 0f, y = size.height),
                    end = Offset(x = size.width, y = size.height),
                    strokeWidth = 1.dp.toPx(),
                    cap = StrokeCap.Square
                )
            }
            .clickable { onClick.invoke() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        HasText(
            modifier = Modifier.padding(end = 6.dp),
            text = highlightTextBuilder(
                fullText = name,
                highlightText = searchText,
            ),
            fontSize = 14.dp
        )
        if(isSelected) {
            Image(
                modifier = Modifier.size(14.dp),
                painter = painterResource(id = R.drawable.btn_tag_select),
                contentDescription = "tag select"
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        HasText(
            text = cnt,
            color = Color(0xFF707070),
            fontSize = 12.dp,
            fontWeight = HasFontWeight.Thin,
        )
    }
}

@DevicePreviews
@Composable
fun TagSearchItemPreview() {
    Column {
        TagSearchItem(
            searchText = "B",
            name = "ABCD",
            cnt = "+999",
            isSelected = true,
            onClick = {}
        )
        TagSearchItem(
            searchText = "C",
            name = "ABCD",
            cnt = "+999",
            isSelected = false,
            onClick = {}
        )
    }
}