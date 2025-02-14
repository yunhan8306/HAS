package com.has.android.feature.myPage.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.has.android.common.resource.R

@Composable
fun ProfileNoneImage() {
    Image(
        painter = painterResource(id = if(isSystemInDarkTheme()) R.drawable.img_my_profile_none_dark else R.drawable.img_my_profile_none_light),
        contentDescription = "profile image none"
    )
}