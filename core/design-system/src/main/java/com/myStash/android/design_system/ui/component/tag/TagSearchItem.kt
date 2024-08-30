package com.myStash.android.design_system.ui.component.tag

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.myStash.android.common.resource.R
import com.myStash.android.common.util.highlightTextBuilder
import com.myStash.android.design_system.ui.DevicePreviews
import com.myStash.android.design_system.ui.color.ColorFamilyGray200AndGray600
import com.myStash.android.design_system.ui.color.ColorFamilyLime100AndGray600
import com.myStash.android.design_system.ui.color.ColorFamilyLime500AndLime300
import com.myStash.android.design_system.ui.color.ColorFamilyWhiteAndBlack
import com.myStash.android.design_system.ui.color.Gray400
import com.myStash.android.design_system.ui.component.text.HasFontWeight
import com.myStash.android.design_system.ui.component.text.HasText

@Composable
fun TagSearchItem(
    searchText: String = "",
    name: String,
    cnt: String = "",
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val drawLineColor = ColorFamilyGray200AndGray600

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .background(if (isSelected) ColorFamilyLime100AndGray600 else ColorFamilyWhiteAndBlack)
            .drawBehind {
                drawLine(
                    color = drawLineColor,
                    start = Offset(x = 0f, y = size.height),
                    end = Offset(x = size.width, y = size.height),
                    strokeWidth = 1.dp.toPx(),
                    cap = StrokeCap.Square
                )
            }
            .padding(horizontal = 12.dp)
            .clickable { onClick.invoke() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        HasText(
            modifier = Modifier.padding(end = 6.dp),
            text = highlightTextBuilder(
                fullText = name,
                highlightText = searchText,
                highlightColor = ColorFamilyLime500AndLime300
            ),
            fontSize = 14.dp
        )
        if(isSelected) {
            Image(
                painter = painterResource(id = if(isSystemInDarkTheme()) R.drawable.img_search_select_dark else R.drawable.img_search_select_light),
                contentDescription = "tag select"
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        HasText(
            text = cnt,
            color = Gray400,
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