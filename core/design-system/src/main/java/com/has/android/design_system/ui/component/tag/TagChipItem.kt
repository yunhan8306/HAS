package com.has.android.design_system.ui.component.tag

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.has.android.design_system.ui.DevicePreviews
import com.has.android.design_system.ui.color.ColorFamilyBlack20AndGray450
import com.has.android.design_system.ui.color.ColorFamilyBlack20AndLime300
import com.has.android.design_system.ui.color.ColorFamilyGray200AndBlack20
import com.has.android.design_system.ui.color.ColorFamilyGray200AndGray400
import com.has.android.design_system.ui.color.ColorFamilyLime300AndBlack20
import com.has.android.design_system.ui.color.Lime300
import com.has.android.design_system.ui.component.text.HasText

@Composable
fun TagChipItem(
    name: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier.padding(4.dp)
    ) {
        Box(
            modifier = Modifier
                .height(28.dp)
                .clip(shape = RoundedCornerShape(size = 15.dp))
                .background(color = if(isSelected) ColorFamilyLime300AndBlack20 else ColorFamilyGray200AndBlack20)
                .border(width = 1.dp, color = if(isSelected) Lime300 else ColorFamilyGray200AndGray400, shape = RoundedCornerShape(size = 15.dp))
                .padding(horizontal = 12.dp)
                .clickable { onClick.invoke() },
            contentAlignment = Alignment.Center
        ) {
            HasText(
                text = name,
                fontSize = 14.dp,
                color = if(isSelected) ColorFamilyBlack20AndLime300 else ColorFamilyBlack20AndGray450
            )
        }
    }
}

@DevicePreviews
@Composable
fun TagSelectChipItemPreview() {
    Row {
        TagChipItem(
            name = "test",
            isSelected = true,
            onClick = {}
        )
        TagChipItem(
            name = "test",
            isSelected = false,
            onClick = {}
        )
    }
}