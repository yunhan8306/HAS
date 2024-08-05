package com.myStash.android.design_system.ui.component.modal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.myStash.android.common.util.isNotNull
import com.myStash.android.core.model.StyleScreenModel
import com.myStash.android.design_system.ui.component.button.HasSelectButton
import com.myStash.android.design_system.ui.theme.clickableNoRipple

@Composable
fun StyleBottomModel(
    selectedStyle: StyleScreenModel?,
    onSelect: () -> Unit,
    onCancel: () -> Unit,
) {
    var isShow by remember { mutableStateOf(false) }

    LaunchedEffect(selectedStyle) {
        isShow = selectedStyle.isNotNull()
    }

    if(isShow) {
        Column(
            modifier = Modifier
                .shadow(
                    elevation = 10.dp,
                    spotColor = Color(0x29000000),
                    ambientColor = Color(0x29000000)
                )
                .fillMaxWidth()
                .height(84.dp)
                .background(
                    color = Color(0xFFFFFFFF),
                    shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                )
                .clickableNoRipple { }
                .padding(16.dp)
        ) {
            HasSelectButton(
                modifier = Modifier.padding(top = 16.dp),
                selectText = "피드 등록하기",
                onSelect = onSelect,
                onCancel = onCancel
            )
        }
    }
}