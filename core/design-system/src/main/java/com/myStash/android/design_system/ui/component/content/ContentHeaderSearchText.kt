package com.myStash.android.design_system.ui.component.content

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.myStash.android.common.resource.R
import com.myStash.android.design_system.ui.DevicePreviews
import com.myStash.android.design_system.ui.color.ColorFamilyGray200AndGray600
import com.myStash.android.design_system.ui.color.ColorFamilyWhiteAndGray600
import com.myStash.android.design_system.ui.color.Gray900
import com.myStash.android.design_system.ui.component.text.HasText
import com.myStash.android.design_system.util.clickableRipple

@Composable
fun ContentHeaderSearchText(
    text: String,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(44.dp)
                .background(color = ColorFamilyGray200AndGray600, shape = RoundedCornerShape(size = 10.dp))
                .clickableRipple(onClick = onClick)
                .padding(start = 12.dp, end = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            HasText(
                modifier = Modifier.weight(1f),
                text = text,
                color = Gray900
            )
            Image(
                modifier = Modifier.size(24.dp),
                painter = painterResource(id = R.drawable.btn_search),
                contentDescription = "btn search"
            )
        }
    }
}

@DevicePreviews
@Composable
fun ContentHeaderTextFieldPreview() {
    ContentHeaderSearchText(
        text = "원하는 태그를 검색해 보세요",
        onClick = {}
    )
}