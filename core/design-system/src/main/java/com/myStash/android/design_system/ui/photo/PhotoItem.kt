package com.myStash.android.design_system.ui.photo

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
fun PhotoItem(
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(100.dp)
            .background(color = Color(0xFFF1F1F1), shape = RoundedCornerShape(size = 10.dp))
            .clickable { onClick.invoke() },
        contentAlignment = Alignment.Center
    ) {
        Column {
            Image(
                modifier = Modifier.size(32.dp),
                painter = painterResource(id = R.drawable.img_photo),
                contentDescription = "photo"
            )
            HasText(
                modifier = Modifier.padding(top = 8.dp),
                text = "Photo",
                color = Color(0xFFA4A4A4),
                fontSize = 12.dp
            )
        }
    }
}

@DevicePreviews
@Composable
fun PhotoItemPreview() {
    PhotoItem(
        onClick = {}
    )
}