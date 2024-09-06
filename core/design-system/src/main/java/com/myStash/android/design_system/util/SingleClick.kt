package com.myStash.android.design_system.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flow

private const val SINGLE_CLICK_DURATION_DEFAULT = 300L
private const val BOOLEAN_SINGLE_CLICK_DURATION_DEFAULT = 100L

interface SingleClickEventListener {
    fun onClick(action: () -> Unit)
    fun onClickBoolean(action: (Boolean) -> Unit)
}
@Composable
fun <T> singleClick(
    duration: Long = SINGLE_CLICK_DURATION_DEFAULT,
    content: @Composable (SingleClickEventListener) -> T
): T {
    val debounceState = remember {
        MutableSharedFlow<() -> Unit>(
            extraBufferCapacity = 1,
            onBufferOverflow = BufferOverflow.DROP_OLDEST
        )
    }

    val booleanDebounceState = remember {
        MutableSharedFlow<(Boolean) -> Unit>(
            extraBufferCapacity = 1,
            onBufferOverflow = BufferOverflow.DROP_OLDEST
        )
    }

    val result = content.invoke(
        object : SingleClickEventListener {
            override fun onClick(action: () -> Unit) {
                debounceState.tryEmit(action)
            }

            override fun onClickBoolean(action: (Boolean) -> Unit) {
                booleanDebounceState.tryEmit(action)
            }
        }
    )

    LaunchedEffect(Unit) {
        debounceState
            .throttleFirst(duration)
            .collect { it.invoke() }
    }

    LaunchedEffect(Unit){
        booleanDebounceState
            .throttleFirst(BOOLEAN_SINGLE_CLICK_DURATION_DEFAULT)
            .collect { it.invoke(false) }
    }

    return result
}

fun <T> Flow<T>.throttleFirst(windowDuration: Long): Flow<T> = flow {
    var lastEmissionTime = 0L
    collect { upstream ->
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastEmissionTime > windowDuration) {
            lastEmissionTime = currentTime
            emit(upstream)
        }
    }
}