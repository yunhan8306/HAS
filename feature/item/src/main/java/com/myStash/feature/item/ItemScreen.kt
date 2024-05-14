package com.myStash.feature.item

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text2.BasicTextField2
import androidx.compose.foundation.text2.input.rememberTextFieldState
import androidx.compose.foundation.text2.input.textAsFlow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.myStash.common.compose.activityViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn

@Composable
fun ItemScreen(
    viewModel: ItemViewModel = activityViewModel()
) {
    val tagInputState = viewModel.addTagState

    LaunchedEffect(tagInputState) {
        viewModel.addTagStateFlow

    }

    val textState = remember { mutableStateOf("") }

    val userInput = remember { mutableStateOf("") }

    // userInput의 변경을 감지하여 플로우를 생성
    val userInputFlow = snapshotFlow { userInput.value }




    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Blue)
    ) {
        Text(
            "test",
            fontSize = 50.sp,
            color = Color.Black
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Gray)
    ) {

        BasicTextField2(state = tagInputState)
        ItemTextField(
            textState = tagInputState,
            hintText = "힌트",
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Yellow),
        )
    }
}