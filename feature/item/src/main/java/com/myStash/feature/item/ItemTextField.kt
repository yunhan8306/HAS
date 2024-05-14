package com.myStash.feature.item

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
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

@Composable
fun ItemTextField(
    textState: TextFieldState,
    hintText: String = "",
    modifier: Modifier = Modifier,
) {

    BasicTextField2(
        modifier = modifier.fillMaxWidth(),
        state = textState,
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
        textStyle = TextStyle(color = Color(0xFF848484)),
        decorator = { innerTextField ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Spacer(modifier = Modifier.width(17.dp))
                if (textState.text.isEmpty()) {
                    Text(
                        text = hintText,
                        color = Color(0xFF848484),
                    )
                }
                innerTextField()
            }
        },
    )
}