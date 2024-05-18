package com.myStash.feature.item

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text2.BasicTextField2
import androidx.compose.foundation.text2.input.TextFieldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.myStash.design_system.ui.DevicePreviews

@Composable
fun ItemTextField(
    modifier: Modifier = Modifier,
    fontSize: TextUnit = 17.sp,
    textState: TextFieldState = TextFieldState(),
    hintText: String = "",
) {

    BasicTextField2(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(30.dp))
            .border(
                width = 1.dp,
                color = Color.Black,
                RoundedCornerShape(30.dp)
            )
            .background(Color.Gray),
        state = textState,
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
        textStyle = TextStyle(
            color = Color.White,
            fontSize = fontSize
        ),
        decorator = { innerTextField ->
            Row(
                modifier.padding(vertical = 5.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Spacer(modifier = Modifier.width(17.dp))
                if (textState.text.isEmpty()) {
                    Text(
                        text = hintText,
                        color = Color.White,
                        fontSize = fontSize
                    )
                }
                innerTextField()
            }
        },
    )
}

@DevicePreviews
@Composable
fun ItemTextFieldPreView() {
    ItemTextField(
        hintText = "hint"
    )
}