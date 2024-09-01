package com.myStash.android.feature.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.myStash.android.common.resource.R
import com.myStash.android.design_system.ui.DevicePreviews
import com.myStash.android.design_system.ui.color.ColorFamilyWhiteAndGray800

@Composable
fun SplashScreen(

) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ColorFamilyWhiteAndGray800)
            .padding(bottom = 48.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Image(
            modifier = Modifier.size(226.dp),
            painter = painterResource(id = com.myStash.android.common.resource.R.drawable.img_app_logo),
            contentDescription = "has logo"
        )
        Spacer(modifier = Modifier.weight(1f))
        Image(
            modifier = Modifier.width(90.dp).height(24.dp),
            painter = painterResource(id = if(isSystemInDarkTheme()) R.drawable.img_has_logo_dark else R.drawable.img_has_logo_light),
            contentDescription = "has logo"
        )
    }
}

@DevicePreviews
@Composable
fun SplashScreenPreview() {
    SplashScreen()
}