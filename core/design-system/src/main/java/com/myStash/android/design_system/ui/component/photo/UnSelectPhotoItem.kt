package com.myStash.android.design_system.ui.component.photo

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
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
import com.myStash.android.common.util.isNotNull
import com.myStash.android.design_system.ui.DevicePreviews
import com.myStash.android.design_system.ui.color.ColorFamilyGray200AndGray600
import com.myStash.android.design_system.ui.color.ColorFamilyGray900AndGray400
import com.myStash.android.design_system.ui.color.ColorFamilyLime700AndLime300
import com.myStash.android.design_system.ui.component.text.HasText

@Composable
fun UnselectPhotoItem(
    cnt: Int? = null,
    onClick: () -> Unit
) {
    Row {
        Box(
            modifier = Modifier
                .size(100.dp)
                .background(
                    color = ColorFamilyGray200AndGray600,
                    shape = RoundedCornerShape(size = 10.dp)
                )
                .clickable { onClick.invoke() },
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = if(isSystemInDarkTheme()) R.drawable.img_empty_photo_dark else R.drawable.img_empty_photo_light),
                    contentDescription = "unselect photo"
                )
                Row(
                    modifier = Modifier.padding(top = 8.dp),
                ) {
                    if(cnt.isNotNull()) {
                        HasText(
                            text = "(",
                            color = ColorFamilyGray900AndGray400,
                            fontSize = 12.dp
                        )
                        HasText(
                            text = cnt.toString(),
                            color = ColorFamilyLime700AndLime300,
                            fontSize = 12.dp
                        )
                        HasText(
                            text = "/5)",
                            color = ColorFamilyGray900AndGray400,
                            fontSize = 12.dp
                        )
                    } else {
                        HasText(
                            text = "Photo",
                            color = ColorFamilyGray900AndGray400,
                            fontSize = 12.dp
                        )
                    }
                }
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