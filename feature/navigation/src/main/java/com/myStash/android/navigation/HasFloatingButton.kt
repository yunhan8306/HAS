package com.myStash.android.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.myStash.android.design_system.ui.theme.clickableNoRipple

@Composable
fun HasFloatingButton(
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier.padding(top = 25.dp),
    ) {
        Image(
            modifier = Modifier
                .size(52.dp)
                .clickableNoRipple(onClick = onClick),
            painter = painterResource(id = com.myStash.android.common.resource.R.drawable.img_nav_add),
            contentDescription = "add item"
        )
    }
}