package com.has.android.feature.splash.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.has.android.common.resource.R
import com.has.android.design_system.ui.DevicePreviews

@Composable
fun SplashScreen(

) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(bottom = 48.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Image(
            painter = painterResource(id = if(isSystemInDarkTheme()) R.drawable.img_splash_app_logo_dark else R.drawable.img_splash_app_logo_light),
            contentDescription = "app logo"
        )
        Spacer(modifier = Modifier.weight(1f))
        Image(
            painter = painterResource(id = if(isSystemInDarkTheme()) R.drawable.img_splash_has_logo_dark else R.drawable.img_splash_has_logo_light),
            contentDescription = "has logo"
        )
    }
}

@DevicePreviews
@Composable
fun SplashScreenPreview() {
    SplashScreen()
}