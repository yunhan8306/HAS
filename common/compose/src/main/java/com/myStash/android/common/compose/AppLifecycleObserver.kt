package com.myStash.android.common.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver

@Composable
fun AppLifecycleObserver(
    onForeground: () -> Unit = {},
    onBackground: () -> Unit = {}
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val lifecycleObserver = rememberLifecycleObserver(onForeground, onBackground)

    DisposableEffect(lifecycleOwner) {
        val lifecycle = lifecycleOwner.lifecycle
        lifecycle.addObserver(lifecycleObserver)

        onDispose {
            lifecycle.removeObserver(lifecycleObserver)
        }
    }
}

@Composable
fun rememberLifecycleObserver(
    onForeground: () -> Unit,
    onBackground: () -> Unit
): LifecycleEventObserver {
    return remember {
        LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_START -> onForeground()
                Lifecycle.Event.ON_STOP -> onBackground()
                else -> {}
            }
        }
    }
}