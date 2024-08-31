package com.myStash.android.design_system.ui.component.type

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.myStash.android.design_system.ui.DevicePreviews
import com.myStash.android.design_system.ui.color.ColorFamilyBlackAndWhite
import com.myStash.android.design_system.ui.color.ColorFamilyWhiteAndBlack
import com.myStash.android.design_system.ui.component.text.HasText

@Composable
fun TypeChipItem(
    name: String
) {
    Box(
        modifier = Modifier
            .background(color = ColorFamilyBlackAndWhite, shape = RoundedCornerShape(size = 10.dp))
            .height(20.dp)
            .padding(start = 8.dp, end = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        HasText(
            text = name,
            color = ColorFamilyWhiteAndBlack,
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