package com.has.android.design_system.ui.component.modal

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import com.has.android.common.util.isNotNull
import com.has.android.core.model.StyleScreenModel
import com.has.android.design_system.ui.component.button.HasSelectButton
import com.has.android.design_system.ui.theme.clickableNoRipple

@Composable
fun StyleBottomModal(
    isShow: Boolean,
    onSelect: () -> Unit,
    onCancel: () -> Unit,
) {
    val modalHeight by animateDpAsState(
        targetValue = if(isShow) 84.dp else 0.dp,
        animationSpec = tween(400),
        label = "modal height"
    )

    Column(
        modifier = Modifier
            .shadow(elevation = 10.dp, shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
            .background(color = MaterialTheme.colors.surface, shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
            .fillMaxWidth()
            .height(modalHeight)
            .clickableNoRipple { }
            .padding(16.dp)
    ) {
        HasSelectButton(
            selectText = "등록하기",
            onSelect = onSelect,
            onCancel = onCancel
        )
    }
}