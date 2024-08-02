package com.myStash.android.design_system.ui.component.dialog

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Dialog
import com.myStash.android.core.model.StyleScreenModel
import com.myStash.android.core.model.Tag

@Composable
fun StyleMoreDialog(
    styleScreenModel: StyleScreenModel?,
    tagTotalList: List<Tag>,
    onDismiss: () -> Unit = {}
) {
    styleScreenModel?.let { style ->
        Dialog(
            onDismissRequest = onDismiss
        ) {

        }
    }
}