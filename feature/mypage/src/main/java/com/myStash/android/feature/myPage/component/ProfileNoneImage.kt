package com.myStash.android.feature.myPage.component

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.myStash.android.common.resource.R

@Composable
fun ProfileNoneImage() {
    Image(
        painter = painterResource(id = R.drawable.img_my_profile_none),
        contentDescription = "profile image none"
    )
}