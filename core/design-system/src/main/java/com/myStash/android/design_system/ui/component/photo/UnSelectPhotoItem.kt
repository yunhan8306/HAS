package com.myStash.android.design_system.ui.component.photo

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.myStash.android.common.resource.R
import com.myStash.android.design_system.ui.DevicePreviews
import com.myStash.android.design_system.ui.color.ColorFamilyGray200AndGray600
import com.myStash.android.design_system.ui.color.ColorFamilyGray900AndGray400
import com.myStash.android.design_system.ui.component.text.HasText

@Composable
fun UnselectPhotoItem(
    onClick: () -> Unit
) {
    Row {
        Box(
            modifier = Modifier
                .size(100.dp)
                .background(color = ColorFamilyGray200AndGray600, shape = RoundedCornerShape(size = 10.dp))
                .clickable { onClick.invoke() },
            contentAlignment = Alignment.Center
        ) {
            Column {
                Image(
                    modifier = Modifier.size(32.dp),
                    painter = painterResource(id = R.drawable.img_photo),
                    contentDescription = "unselect photo"
                )
                HasText(
                    modifier = Modifier.padding(top = 8.dp),
                    text = "Photo",
                    color = ColorFamilyGray900AndGray400,
                    fontSize = 12.dp
                )
            }
        }
        Box(modifier = Modifier.padding(end = 6.dp))
    }
}

@DevicePreviews
@Composable
fun PhotoItemPreview() {
    UnselectPhotoItem(
        onClick = {}
    )
}