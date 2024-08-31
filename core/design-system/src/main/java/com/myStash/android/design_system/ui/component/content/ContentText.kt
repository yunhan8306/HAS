package com.myStash.android.design_system.ui.component.content

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.myStash.android.common.resource.R
import com.myStash.android.design_system.ui.DevicePreviews
import com.myStash.android.design_system.ui.color.ColorFamilyBlack20AndWhite
import com.myStash.android.design_system.ui.color.ColorFamilyGray300AndGray600
import com.myStash.android.design_system.ui.color.ColorFamilyGray900AndGray400
import com.myStash.android.design_system.ui.color.ColorFamilyWhiteAndBlack20
import com.myStash.android.design_system.ui.component.text.HasText
import com.myStash.android.design_system.ui.theme.clickableNoRipple

@Composable
fun ContentText(
    text: String,
    isTextFocus: Boolean = false,
    isBorderFocus: Boolean = false,
    onClick: () -> Unit,
    endContent: @Composable () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .border(width = 1.dp, color = if (isBorderFocus) ColorFamilyBlack20AndWhite else ColorFamilyGray300AndGray600, shape = RoundedCornerShape(size = 10.dp))
            .fillMaxWidth()
            .height(44.dp)
            .clip(shape = RoundedCornerShape(size = 10.dp))
            .background(ColorFamilyWhiteAndBlack20)
            .clickableNoRipple(onClick = onClick)
            .padding(start = 12.dp, end = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        HasText(
            modifier = Modifier.weight(1f),
            text = text,
            color = if(isTextFocus) ColorFamilyBlack20AndWhite else ColorFamilyGray900AndGray400
        )
        endContent.invoke()
    }
}

@DevicePreviews
@Composable
fun ContentTextPreview() {
    Column {
        ContentText(
            text = "Content Text",
            isTextFocus = true,
            isBorderFocus = true,
            onClick = {},
            endContent = {
                Image(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(id = R.drawable.btn_drop_down_dark),
                    contentDescription = "btn down"
                )
            }
        )
        ContentText(
            text = "Content Text",
            isTextFocus = false,
            isBorderFocus = false,
            onClick = {},
            endContent = {
                Image(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(id = R.drawable.btn_drop_down_dark),
                    contentDescription = "btn down"
                )
            }
        )
    }
}