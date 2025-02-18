package com.has.android.design_system.ui.component.button

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.has.android.design_system.ui.DevicePreviews
import com.has.android.design_system.ui.color.ColorFamilyBlack20AndLime300
import com.has.android.design_system.ui.color.ColorFamilyLime300AndBlack20
import com.has.android.design_system.ui.color.ColorFamilyWhiteAndGray400
import com.has.android.design_system.ui.color.ColorGray50AndGray550
import com.has.android.design_system.ui.component.text.HasFontWeight
import com.has.android.design_system.ui.component.text.HasText

@Composable
fun HasButton(
    modifier: Modifier = Modifier,
    text: String,
    isComplete: Boolean = true,
    onClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
            .background(
                color = if(isComplete) ColorFamilyBlack20AndLime300 else ColorGray50AndGray550,
                shape = RoundedCornerShape(size = 10.dp)
            )
            .clickable { if(isComplete) onClick.invoke() },
        contentAlignment = Alignment.Center
    ) {
        HasText(
            text = text,
            color = if(isComplete) ColorFamilyLime300AndBlack20 else ColorFamilyWhiteAndGray400,
            fontSize = 16.dp,
            fontWeight = HasFontWeight.Bold,
        )
    }
}

@DevicePreviews
@Composable
fun HasButtonPreview() {
    Column {
        HasButton(
            text = "Button",
            isComplete = true,
            onClick = {}
        )
        HasButton(
            text = "Button",
            isComplete = false,
            onClick = {}
        )
    }
}