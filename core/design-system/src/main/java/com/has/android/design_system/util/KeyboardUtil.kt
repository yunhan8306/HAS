package com.has.android.design_system.util

import android.app.Activity
import android.graphics.Rect
import android.view.ViewTreeObserver
import android.view.Window
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

@Composable
fun KeyboardState(isOpen: suspend (Boolean) -> Unit) {
    val context = LocalContext.current
    val activity = context as Activity
    val window = activity.window
    val keyboardController = LocalSoftwareKeyboardController.current
    val keyboardObserver = KeyboardEventObserver(window)

    LaunchedEffect(keyboardController) {
        keyboardObserver.keyboardState.collect { isOpen ->
            isOpen(isOpen)
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            keyboardObserver.dispose()
        }
    }
}

class KeyboardEventObserver(window: Window) {
    private val _keyboardState = MutableStateFlow(false)
    val keyboardState = _keyboardState.asStateFlow()

    private val rootView = window.decorView.rootView
    private val listener = ViewTreeObserver.OnGlobalLayoutListener {
        val rect = Rect()
        rootView.getWindowVisibleDisplayFrame(rect)
        val screenHeight = rootView.height
        val keypadHeight = screenHeight - rect.bottom

        _keyboardState.value = keypadHeight > screenHeight * 0.15
    }

    init {
        rootView.viewTreeObserver.addOnGlobalLayoutListener(listener)
    }

    fun dispose() {
        rootView.viewTreeObserver.removeOnGlobalLayoutListener(listener)
    }
}