package com.myStash.android.feature.item

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text2.BasicTextField2
import androidx.compose.foundation.text2.input.TextFieldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBottomSheet(
    state: TextFieldState,
    deleteInput: () -> Unit,
    onDismissRequest: () -> Unit,
) {
    ModalBottomSheet(
        containerColor = Color.White,
        contentColor = Color.White,
        dragHandle = null,
        onDismissRequest = onDismissRequest
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 24.dp)
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                ItemSelectRow {
                    BasicTextField2(
//                    modifier = Modifier
//                        .border(
//                            width = 1.dp,
//                            color = Color(0xFF202020),
//                            shape = RoundedCornerShape(size = 10.dp)
//                        )
//                        .width(328.dp)
//                        .height(44.dp)
//                        .background(
//                            color = Color(0xFFFFFFFF),
//                            shape = RoundedCornerShape(size = 10.dp)
//                        ),
                        state = state
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Box(modifier = Modifier
                        .size(24.dp)
                        .clickable { deleteInput.invoke() })
                }
            }
        }
    }

//    ScaffoldBottomSheet() {
//
//    }
}