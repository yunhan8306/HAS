package com.myStash.android.feature.manage.type

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.hilt.navigation.compose.hiltViewModel
import com.myStash.android.common.util.isNotNull
import com.myStash.android.core.model.Type
import com.myStash.android.design_system.ui.component.dialog.HasConfirmDialog
import com.myStash.android.feature.manage.tag.ManageTagSideEffect
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun ManageTypeRoute(
    viewModel: ManageTypeViewModel = hiltViewModel(),
) {
    val activity = LocalContext.current as ComponentActivity
    val focusManager = LocalFocusManager.current
    val state by viewModel.collectAsState()
    val scrollState: LazyListState = rememberLazyListState()
    var removeType: Type? by remember { mutableStateOf(null) }

    viewModel.collectSideEffect { sideEffect ->
        when(sideEffect) {
            is ManageTypeSideEffect.ScrollDown -> {
                focusManager.clearFocus()
                scrollState.animateScrollBy(5000f)
            }
        }
    }

    LaunchedEffect(viewModel.addTypeTextState.text) {
        scrollState.animateScrollBy(0f)
    }

    LaunchedEffect(removeType) {
        focusManager.clearFocus()
    }

    ManageTypeScreen(
        state = state,
        scrollState = scrollState,
        addTypeTextState = viewModel.addTypeTextState,
        onAction = { action ->
            when(action) {
                is ManageTypeAction.RemoveType -> {
                    removeType = action.type
                }
                else -> {
                    viewModel.onAction(action)
                }
            }
        }
    )

    HasConfirmDialog(
        isShow = removeType.isNotNull(),
        title = "카테고리 삭제",
        content = "\"${removeType?.name}\" 삭제 할 거?",
        confirmText = "확인",
        dismissText = "취소",
        onConfirm = {
            viewModel.onAction(ManageTypeAction.RemoveType(removeType!!))
            removeType = null
        },
        onDismiss = { removeType = null }
    )

    BackHandler {
        when {
            state.focusType.isNotNull() -> {
                viewModel.onAction(ManageTypeAction.FocusType(null))
            }
            else -> {
                activity.finish()
            }
        }
    }
}