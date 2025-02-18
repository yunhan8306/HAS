package com.has.android.design_system.ui.component.type

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.has.android.design_system.ui.DevicePreviews
import com.has.android.design_system.ui.color.ColorFamilyBlack20AndWhite
import com.has.android.design_system.ui.color.ColorFamilyWhiteAndBlack20
import com.has.android.design_system.ui.component.text.HasText

@Composable
fun TypeChipItem(
    name: String
) {
    Box(
        modifier = Modifier
            .background(color = ColorFamilyBlack20AndWhite, shape = RoundedCornerShape(size = 10.dp))
            .height(20.dp)
            .padding(start = 8.dp, end = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        HasText(
            text = name,
            color = ColorFamilyWhiteAndBlack20,
            fontSize = 14.dp
        )
    }
}

@DevicePreviews
@Composable
fun TypeChipItemPreview() {
    TypeChipItem(
        name = "test"
    )
}