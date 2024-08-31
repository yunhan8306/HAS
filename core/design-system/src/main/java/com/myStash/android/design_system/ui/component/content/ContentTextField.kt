package com.myStash.android.design_system.ui.component.content

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.myStash.android.common.resource.R
import com.myStash.android.design_system.ui.DevicePreviews
import com.myStash.android.design_system.ui.color.ColorFamilyBlack20AndWhite
import com.myStash.android.design_system.ui.color.ColorFamilyGray300AndGray600
import com.myStash.android.design_system.ui.color.ColorFamilyGray900AndGray400
import com.myStash.android.design_system.ui.color.ColorFamilyWhiteAndBlack20
import com.myStash.android.design_system.ui.component.text.HasText

@Composable
fun ContentTextField(
    textState: TextFieldState,
    hint: String,
    isBack: Boolean = false,
    back: () -> Unit = {},
    delete: () -> Unit
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
                    if(isBack) {
                        Image(
                            modifier = Modifier
                                .padding(end = 8.dp)
                                .clickable { back.invoke() },
                            painter = painterResource(id = if(isSystemInDarkTheme()) R.drawable.btn_back_dark else R.drawable.btn_back_light),
                            contentDescription = "back"
                        )
                    }
                    if(textState.text.isEmpty()) {
                        HasText(
                            text = hint,
                            color = ColorFamilyGray900AndGray400
                        )
                    } else {
                        innerTextField()
                        Spacer(modifier = Modifier.weight(1f))
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .clickable { delete.invoke() },
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.btn_delete),
                                contentDescription = "input delete"
                            )
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
        ContentTextField(
            textState = TextFieldState(),
            hint = "원하는 태그를 검색해 보세요",
            isBack = true,
            back = {},
            delete = {}
        )
        ContentTextField(
            textState = TextFieldState("ABCD"),
            hint = "원하는 태그를 검색해 보세요",
            isBack = true,
            back = {},
            delete = {}
        )
        ContentTextField(
            textState = TextFieldState(),
            hint = "원하는 태그를 검색해 보세요",
            isBack = false,
            back = {},
            delete = {}
        )
        ContentTextField(
            textState = TextFieldState("ABCD"),
            hint = "원하는 태그를 검색해 보세요",
            isBack = false,
            back = {},
            delete = {}
        )
    }
}