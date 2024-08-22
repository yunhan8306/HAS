package com.myStash.android.feature.manage.type

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text2.input.TextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.myStash.android.design_system.ui.component.content.ContentTextField
import com.myStash.android.design_system.util.addFocusCleaner
import com.myStash.android.feature.manage.component.ManageAware
import com.myStash.android.feature.manage.component.TypeTextField
import org.orbitmvi.orbit.compose.collectAsState

@Composable
fun ManageTypeRoute(
    viewModel: ManageTypeViewModel = hiltViewModel(),
) {
    val state by viewModel.collectAsState()

    ManageTypeScreen(
        state = state,
        addTypeTextState = viewModel.addTypeTextState,
        onAction = viewModel::onAction
    )
}

@Composable
fun ManageTypeScreen(
    state: ManageTypeState,
    addTypeTextState: TextFieldState,
    onAction: (ManageTypeAction) -> Unit
) {

    val focusManager = LocalFocusManager.current

    ManageAware {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .addFocusCleaner(focusManager)
        ) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp)
                        .padding(top = 12.dp)
                ) {
                    TypeTextField(
                        textState = addTypeTextState,
                        hint = "원하는 카테고리를 등록하세요.",
                        add = { onAction.invoke(ManageTypeAction.AddType) }
                    )
                }
            }
            items(
                items = state.typeTotalList,
                key = { it.id!! }
            ) {

            }
        }


        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
        ) {

        }
    }
}