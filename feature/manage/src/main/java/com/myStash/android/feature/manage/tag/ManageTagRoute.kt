package com.myStash.android.feature.manage.tag

import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.hilt.navigation.compose.hiltViewModel
import com.myStash.android.common.util.isNotNull
import com.myStash.android.core.model.Tag
import com.myStash.android.core.model.Type
import com.myStash.android.design_system.ui.component.dialog.HasConfirmDialog
import com.myStash.android.feature.manage.type.ManageTypeAction
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun ManageTagRoute(
    viewModel: ManageTagViewModel = hiltViewModel(),
) {
    val activity = LocalContext.current as ComponentActivity
    val focusManager = LocalFocusManager.current
    val state by viewModel.collectAsState()
    val scrollState: LazyListState = rememberLazyListState()
    var removeTag: Tag? by remember { mutableStateOf(null) }

    viewModel.collectSideEffect { sideEffect ->
        when(sideEffect) {
            is ManageTagSideEffect.ScrollDown -> {
                focusManager.clearFocus()
                scrollState.animateScrollBy(5000f)
            }
        }
    }

    LaunchedEffect(viewModel.addTagTextState.text) {
        scrollState.animateScrollBy(0f)
    }

    LaunchedEffect(removeTag) {
        focusManager.clearFocus()
    }

    ManageTagScreen(
        state = state,
        scrollState = scrollState,
        addTagTextState = viewModel.addTagTextState,
        onAction = { action ->
            when(action) {
                is ManageTagAction.RemoveTag -> {
                    removeTag = action.tag
                }
                else -> {
                    viewModel.onAction(action)
                }
            }
        }
    )

    HasConfirmDialog(
        isShow = removeTag.isNotNull(),
        title = "태그 삭제",
        content = "\"${removeTag?.name}\" 삭제 할 거?",
        confirmText = "확인",
        dismissText = "취소",
        onConfirm = {
            viewModel.onAction(ManageTagAction.RemoveTag(removeTag!!))
            removeTag = null
        },
        onDismiss = { removeTag = null }
    )

    BackHandler {
        when {
            state.focusTag.isNotNull() -> {
                viewModel.onAction(ManageTagAction.FocusTag(null))
            }
            else -> {
                activity.finish()
            }
        }
    }
}