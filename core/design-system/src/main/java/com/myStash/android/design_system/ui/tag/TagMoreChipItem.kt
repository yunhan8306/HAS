package com.myStash.android.design_system.ui.tag

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
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
import com.myStash.android.design_system.ui.HasText

@Composable
fun TagMoreChipItem(
    cnt: String,
    isFold: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier.padding(4.dp)
    ) {
        Row(
            modifier = Modifier
                .height(28.dp)
                .border(
                    width = 1.dp,
                    color = Color(0xFFE1E1E1),
                    shape = RoundedCornerShape(size = 15.dp)
                )
                .background(color = Color(0xFFFFFFFF), shape = RoundedCornerShape(size = 15.dp))
                .padding(horizontal = 12.dp)
                .clickable { onClick.invoke() },
            verticalAlignment = Alignment.CenterVertically
        ) {
            HasText(
                modifier = Modifier.padding(end = 2.dp),
                text = cnt,
                color = Color(0xFFA4A4A4),
                fontSize = 14.dp,
            )
            Image(
                modifier = Modifier.size(14.dp),
                painter = painterResource(id = if(isFold) R.drawable.btn_tag_more else R.drawable.btn_tag_more_fold),
                contentDescription = "tag more"
            )
        }
    }
}

@DevicePreviews
@Composable
fun TagMoreChipItemPreview() {
    Row {
        TagMoreChipItem(
            cnt = "10",
            isFold = true,
            onClick = {}
        )
        TagMoreChipItem(
            cnt = "10",
            isFold = false,
            onClick = {}
        )
    }
}