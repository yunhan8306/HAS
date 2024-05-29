package com.myStash.android.feature.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.myStash.android.design_system.ui.DevicePreviews

@Composable
fun SplashScreen(
    testCount: String,
    skip: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Text(text = "Splash")
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = testCount)
        Spacer(modifier = Modifier.height(10.dp))
        Box(
            modifier = Modifier
                .size(50.dp)
                .background(Color.Gray)
                .clickable { skip.invoke() },
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Skip")
        }
        Spacer(modifier = Modifier.weight(1f))
    }
}

@DevicePreviews
@Composable
fun SplashScreenPreview() {
    SplashScreen("0", {})
}