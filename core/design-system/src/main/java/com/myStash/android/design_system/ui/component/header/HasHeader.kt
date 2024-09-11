package com.myStash.android.design_system.ui.component.header

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.myStash.android.common.resource.R
import com.myStash.android.design_system.ui.DevicePreviews
import com.myStash.android.design_system.ui.component.text.HasFontWeight
import com.myStash.android.design_system.ui.component.text.HasText
import com.myStash.android.design_system.ui.component.text.TextConstant.HAS_TEXT_EMPTY
import com.myStash.android.design_system.ui.theme.clickableNoRipple

@Composable
fun HasHeader(
    modifier: Modifier = Modifier,
    isBack: Boolean = true,
    text: String = HAS_TEXT_EMPTY,
    centerContent: @Composable () -> Unit = {},
    endContent: @Composable () -> Unit = {},
    onBack: () -> Unit = {},
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp)
            .padding(top = 12.dp, start = 12.dp, end = 12.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        if(isBack) {
            Image(
                modifier = Modifier.clickableNoRipple { onBack.invoke() },
                painter = painterResource(id = if(isSystemInDarkTheme()) R.drawable.btn_back_dark else R.drawable.btn_back_light),
                contentDescription = "back",
            )
        }
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            HasText(
                text = text,
                fontSize = 16.dp,
                fontWeight = HasFontWeight.Bold,
            )
            centerContent.invoke()
        }
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.CenterEnd
        ) {
            endContent.invoke()
        }
    }
}

@DevicePreviews
@Composable
fun ItemHeaderPreview() {
    HasHeader(
        text = "등록하기",
        onBack = {}
    )
}