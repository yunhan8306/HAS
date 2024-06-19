package com.myStash.android.design_system.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SearchTextField2(
    modifier: Modifier = Modifier,
    textState: TextFieldState,
    hint: String = "원하는 태그를 검색해 보세요",
) {
    BasicTextField2(
        modifier = modifier
            .border(width = 1.dp, color = Color(0xFF202020), shape = RoundedCornerShape(size = 10.dp))
            .fillMaxWidth()
            .height(44.dp)
            .background(color = Color(0xFFFFFFFF), shape = RoundedCornerShape(size = 10.dp)),
        state = textState,
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
        textStyle = TextStyle(
            color = Color.Black,
            fontSize = 15.sp
        ),
        decorator = { innerTextField ->
            Box(
                modifier = Modifier.padding(start = 12.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                if(textState.text.isEmpty()) {
                    Text(
                        text = hint,
                        color = Color(0xFFA4A4A4)
                    )
                } else {
                    innerTextField()
                }
            }
        },
    )
}