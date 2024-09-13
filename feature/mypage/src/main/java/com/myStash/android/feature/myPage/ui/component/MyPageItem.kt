package com.myStash.android.feature.myPage.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.myStash.android.common.resource.R
import com.myStash.android.design_system.ui.component.text.HasText
import com.myStash.android.design_system.ui.theme.clickableNoRipple

@Composable
fun MyPageItem(
    text: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .padding(start = 20.dp, end = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        HasText(
            modifier = Modifier.weight(1f),
            text = text,
            fontSize = 16.dp
        )
        Image(
            modifier = Modifier
                .size(24.dp)
                .clickableNoRipple { onClick.invoke() },
            painter = painterResource(id = R.drawable.btn_enter),
            contentDescription = "enter"
        )
    }
}