package com.myStash.android.design_system.ui.component.tag

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.myStash.android.common.resource.R
import com.myStash.android.design_system.ui.DevicePreviews
import com.myStash.android.design_system.ui.color.ColorFamilyBlackAndLime300
import com.myStash.android.design_system.ui.color.ColorFamilyLime300AndBlack
import com.myStash.android.design_system.ui.color.Lime300
import com.myStash.android.design_system.ui.component.text.HasText

@Composable
fun TagDeleteChipItem(
    name: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier.padding(4.dp)
    ) {
        Box(
            modifier = Modifier
                .height(28.dp)
                .clip(shape = RoundedCornerShape(size = 15.dp))
                .background(ColorFamilyLime300AndBlack)
                .border(width = 1.dp, color = Lime300, shape = RoundedCornerShape(size = 15.dp))
                .padding(start = 12.dp, end = 8.dp)
                .clickable { onClick.invoke() },
            contentAlignment = Alignment.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                HasText(
                    modifier = Modifier.padding(end = 4.dp),
                    text = name,
                    color = ColorFamilyBlackAndLime300,
                    fontSize = 14.dp,
                )
                Image(
                    painter = painterResource(id = if(isSystemInDarkTheme()) R.drawable.btn_tag_delete_dark else R.drawable.btn_tag_delete_light),
                    contentDescription = "tag delete",
                )
            }
        }
    }
}

@DevicePreviews
@Composable
fun TagDeleteChipItemPreView() {
    TagDeleteChipItem(
        name = "ABCD",
        onClick = {}
    )
}