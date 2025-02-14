package com.has.android.design_system.ui.component.balloon

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.has.android.design_system.ui.color.ColorFamilyGray300AndGray400
import com.has.android.design_system.ui.color.ColorFamilyWhiteAndGray600
import com.has.android.design_system.ui.component.text.HasText
import com.skydoves.balloon.Balloon
import com.skydoves.balloon.BalloonAnimation
import com.skydoves.balloon.compose.Balloon
import com.skydoves.balloon.compose.BalloonWindow


@Composable
fun HasBalloon(
    builder: Balloon.Builder = rememberHasBalloonBuilder(),
    menuList: List<HasBalloonItem>,
    content: @Composable (BalloonWindow) -> Unit
) {
    Balloon(
        modifier = Modifier.padding(end = 10.dp),
        builder = builder,
        balloonContent = {
            LazyColumn(
                modifier = Modifier.width(120.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                itemsIndexed(menuList) {index, menu ->
                    Box(
                        modifier = Modifier
                            .height(44.dp)
                            .clickable(onClick = menu.onClick),
                        contentAlignment = Alignment.Center
                    ) {
                        HasText(
                            text = menu.name,
                        )
                    }
                    if(index != menuList.lastIndex) {
                        Box(
                            modifier = Modifier
                                .background(ColorFamilyGray300AndGray400)
                                .fillMaxWidth()
                                .height(1.dp)
                        )
                    }
                }
            }
        },
        content = content
    )
}

@Composable
fun rememberHasBalloonBuilder(
    key: Any? = null,
    context: Context = LocalContext.current,
    block: Balloon.Builder.() -> Unit = {},
    onDismiss: () -> Unit = {},
): Balloon.Builder {
    val balloonRadius = with(LocalDensity.current) { 12.dp.toPx() }
    val balloonElevation = with(LocalDensity.current) { 10.dp.toPx() }
    val balloonBackgroundColor = ColorFamilyWhiteAndGray600.toArgb()

    return remember(key) {
        Balloon.Builder(context).apply {
            balloonAnimation = BalloonAnimation.FADE
            cornerRadius = balloonRadius
            backgroundColor = balloonBackgroundColor
            isVisibleArrow = false
            dismissWhenClicked = true
            elevation = balloonElevation

            setPadding(4)
            setMarginLeft(90)
            setOnBalloonDismissListener(onDismiss)
            block.invoke(this)
        }
    }
}