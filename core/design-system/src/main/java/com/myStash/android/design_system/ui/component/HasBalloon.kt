package com.myStash.android.design_system.ui.component

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalContext
import com.skydoves.balloon.Balloon
import com.skydoves.balloon.compose.Balloon


@Composable
fun HasBalloon(
    builder: Balloon.Builder = rememberHasBalloonBuilder(),
    offset: Offset,
    balloonContent: @Composable () -> Unit
) {
    Balloon(
        builder = builder,
        balloonContent = balloonContent,
        content = { balloonWindow ->
            balloonWindow.showAlignBottom(
                xOff = offset.x.toInt(),
                yOff = offset.y.toInt()
            )
        }
    )
}

@Composable
fun rememberHasBalloonBuilder(
    key: Any? = null,
    context: Context = LocalContext.current,
    block: Balloon.Builder.() -> Unit = {},
): Balloon.Builder = remember(key) {
    Balloon.Builder(context).apply {
        block.invoke(this)
    }
}