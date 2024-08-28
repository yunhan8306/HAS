package com.myStash.android.design_system.ui.component.text

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text2.BasicTextField2
import androidx.compose.foundation.text2.input.TextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.myStash.android.design_system.ui.color.ColorFamilyBlackAndWhite
import com.myStash.android.design_system.ui.color.ColorFamilyGray900AndGray400

@Composable
fun HasTextField(
    modifier: Modifier = Modifier,
    textState: TextFieldState,
    focusRequester: FocusRequester,
    fontSize: Dp = 15.dp,
    textColor: Color = ColorFamilyBlackAndWhite,
    hint: String,
    hintColor: Color = ColorFamilyGray900AndGray400,
    onFocus: () -> Unit = {}
) {
    val fontSizeSp = with(LocalDensity.current) { fontSize.toSp() }
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    LaunchedEffect(isFocused) {
        if(isFocused) onFocus.invoke()
    }

    BasicTextField2(
        modifier = modifier.focusRequester(focusRequester),
        state = textState,
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
        textStyle = TextStyle(color = textColor, fontSize = fontSizeSp, fontWeight = FontWeight.Medium),
        decorator = { innerTextField ->
            Box(
                contentAlignment = Alignment.TopStart
            ) {
                if(textState.text.isEmpty()) {
                    HasText(
                        text = hint,
                        color = hintColor
                    )
                } else {
                    innerTextField()
                }
            }
        },
        interactionSource = interactionSource
    )
}