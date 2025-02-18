package com.has.android.splash

import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.has.android.MainActivity
import com.has.android.common.util.CommonActivityResultContract
import com.has.android.feature.splash.ui.SplashScreen
import com.has.android.feature.splash.ui.SplashSideEffect
import com.has.android.feature.splash.ui.SplashStatus
import com.has.android.feature.splash.SplashViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun SplashRoute(
    viewModel: SplashViewModel = hiltViewModel(),
) {

    val state by viewModel.collectAsState()

    val activity = LocalContext.current as ComponentActivity
    val mainActivityLauncher = rememberLauncherForActivityResult(
        contract = CommonActivityResultContract(),
        onResult = {}
    )

    viewModel.collectSideEffect { sideEffect ->
        when(sideEffect) {
            is SplashSideEffect.StartMainActivity -> {
                val intent = Intent(activity, MainActivity::class.java)
                mainActivityLauncher.launch(intent)
                activity.finish()
            }
        }
    }

    when(state.status) {
        is SplashStatus.Success -> {
            SplashScreen()
        }
        else -> Unit
    }
}