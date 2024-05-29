package com.myStash.android

import android.content.Intent
import android.window.SplashScreen
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.myStash.android.common.util.CommonActivityResultContract
import com.myStash.android.design_system.animation.slideIn
import com.myStash.android.feature.splash.SplashScreen
import com.myStash.android.feature.splash.SplashSideEffect
import com.myStash.android.feature.splash.SplashViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun SplashRoute(
    viewModel: SplashViewModel = hiltViewModel(),
) {

    val testCount = viewModel.collectAsState().value.testCount

    val activity = LocalContext.current as ComponentActivity
    val itemActivityLauncher = rememberLauncherForActivityResult(
        contract = CommonActivityResultContract(),
        onResult = {}
    )

    viewModel.collectSideEffect { sideEffect ->
        when(sideEffect) {
            is SplashSideEffect.StartMainActivity -> {
                val intent = Intent(activity.apply { slideIn() }, MainActivity::class.java)
                itemActivityLauncher.launch(intent)
                activity.finish()
            }
        }
    }

    SplashScreen(
        testCount = testCount.toString(),
        skip = viewModel::skip
    )
}