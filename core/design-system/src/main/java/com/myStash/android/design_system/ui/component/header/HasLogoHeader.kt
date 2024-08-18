package com.myStash.android.design_system.ui.component.header

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.myStash.android.common.resource.R

@Composable
fun HasLogoHeader() {
    HasHeader(
        isBack = false,
        centerContent = {
            Image(
                modifier = Modifier
                    .width(74.dp)
                    .height(20.dp),
                painter = painterResource(id = if(isSystemInDarkTheme()) R.drawable.img_has_logo_dark else R.drawable.img_has_logo),
                contentDescription = "has logo"
            )
        }
    )
}