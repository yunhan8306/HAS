package com.myStash.android.feature.manage.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text2.BasicTextField2
import androidx.compose.foundation.text2.input.TextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.myStash.android.design_system.ui.DevicePreviews
import com.myStash.android.design_system.ui.color.ColorFamilyBlack20AndWhite
import com.myStash.android.design_system.ui.color.ColorFamilyGray300AndGray600
import com.myStash.android.design_system.ui.color.ColorFamilyGray900AndGray400
import com.myStash.android.design_system.ui.color.ColorFamilyLime700AndGray400
import com.myStash.android.design_system.ui.color.ColorFamilyWhiteAndBlack20
import com.myStash.android.design_system.ui.component.text.HasText

@Composable
fun ManageTextField(
    textState: TextFieldState,
    hint: String,
    add: () -> Unit
) {

    val fontSizeSp = with(LocalDensity.current) { 15.dp.toSp() }
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    BasicTextField2(
        modifier = Modifier
            .border(
                width = 1.dp,
                color = if (isFocused) ColorFamilyBlack20AndWhite else ColorFamilyGray300AndGray600,
                shape = RoundedCornerShape(size = 10.dp)
            )
            .clip(shape = RoundedCornerShape(size = 10.dp))
            .fillMaxWidth()
            .height(44.dp)
            .background(ColorFamilyWhiteAndBlack20)
            .padding(start = 12.dp, end = 10.dp),
        state = textState,
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
        textStyle = TextStyle(color = ColorFamilyBlack20AndWhite, fontSize = fontSizeSp, fontWeight = FontWeight.Medium),
        decorator = { innerTextField ->
            Box(
                contentAlignment = Alignment.CenterStart
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if(textState.text.isEmpty()) {
                        HasText(
                            text = hint,
                            color = ColorFamilyGray900AndGray400
                        )
                    } else {
                        innerTextField()
                        Spacer(modifier = Modifier.weight(1f))
                        if(textState.text.length > 1) {
                            Box(
                                modifier = Modifier
                                    .height(24.dp)
                                    .width(45.dp)
                                    .clickable { add.invoke() },
                                contentAlignment = Alignment.Center
                            ) {
                                HasText(
                                    text = "Add",
                                    color = ColorFamilyLime700AndGray400
                                )
                            }
                        }
                    }
                }
            }
        },
        interactionSource = interactionSource
    )
}

@DevicePreviews
@Composable
fun ContentTextFieldPreview() {
    Column {
        ManageTextField(
            textState = TextFieldState(),
            hint = "원하는 카테고리를 등록하세요.",
            add = {}
        )
        ManageTextField(
            textState = TextFieldState("ABCD"),
            hint = "원하는 카테고리를 등록하세요.",
            add = {}
        )
        ManageTextField(
            textState = TextFieldState(),
            hint = "원하는 카테고리를 등록하세요.",
            add = {}
        )
        ManageTextField(
            textState = TextFieldState("ABCD"),
            hint = "원하는 카테고리를 등록하세요.",
            add = {}
        )
    }
}