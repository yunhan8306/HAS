package com.myStash.android.design_system.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SearchTextField(
    modifier: Modifier = Modifier,
    textState: TextFieldState,
) {
    BasicTextField2(
        modifier = modifier
            .height(40.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(Color(0xFFF5F5F5)),
        state = textState,
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
        textStyle = TextStyle(
            color = Color.Black,
            fontSize = 17.sp
        ),
        decorator = { innerTextField ->
            Box(
                modifier = Modifier.padding(start = 12.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                if(textState.text.isEmpty()) {
                    Text(
                        text = "원하는 태그를 검색해 보세요",
                        color = Color(0xFFA4A4A4)
                    )
                } else {
                    innerTextField()
                }
            }
        },
    )
}