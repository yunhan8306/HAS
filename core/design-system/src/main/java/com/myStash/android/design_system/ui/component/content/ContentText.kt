package com.myStash.android.design_system.ui.component.content

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import com.myStash.android.design_system.ui.component.text.HasText

@Composable
fun ContentText(
    text: String,
    textColor: Color = Color(0xFFA4A4A4),
    borderColor: Color = Color(0xFFE1E1E1),
    onClick: () -> Unit,
    endContent: @Composable () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(size = 10.dp)
            )
            .fillMaxWidth()
            .height(44.dp)
            .background(color = Color(0xFFFFFFFF), shape = RoundedCornerShape(size = 10.dp))
            .padding(start = 12.dp, end = 8.dp)
            .clickable { onClick.invoke() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        HasText(
            modifier = Modifier.weight(1f),
            text = text,
            color = textColor
        )
        endContent.invoke()
    }
}

@DevicePreviews
@Composable
fun ContentTextPreview() {
    ContentText(
        text = "Content Text",
        textColor = Color(0xFFA4A4A4),
        borderColor = Color(0xFFE1E1E1),
        onClick = {},
        endContent = {
            Image(
                modifier = Modifier.size(24.dp),
                painter = painterResource(id = R.drawable.btn_down),
                contentDescription = "btn down"
            )
        }
    )
}