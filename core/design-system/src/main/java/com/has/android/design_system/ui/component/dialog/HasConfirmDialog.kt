package com.has.android.design_system.ui.component.dialog

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.has.android.design_system.ui.DevicePreviews
import com.has.android.design_system.ui.color.ColorFamilyGray500AndGray900
import com.has.android.design_system.ui.color.ColorFamilyWhiteAndGray600
import com.has.android.design_system.ui.color.Gray350
import com.has.android.design_system.ui.component.button.HasButton
import com.has.android.design_system.ui.component.text.HasFontWeight
import com.has.android.design_system.ui.component.text.HasText

@Composable
fun HasConfirmDialog(
    isShow: Boolean,
    title: String,
    content: String,
    confirmText: String,
    dismissText: String? = null,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit = {}
) {
    if(isShow) {
        Dialog(
            properties = DialogProperties(
                usePlatformDefaultWidth = false,
            ),
            onDismissRequest = onDismiss
        ) {

            Column(
                modifier = Modifier
                    .shadow(elevation = 6.dp,)
                    .width(328.dp)
                    .background(color = ColorFamilyWhiteAndGray600, shape = RoundedCornerShape(size = 10.dp))
                    .padding(start = 16.dp, top = 20.dp, end = 16.dp, bottom = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                HasText(
                    modifier = Modifier.padding(bottom = 12.dp),
                    text = title,
                    textAlign = TextAlign.Center
                )
                HasText(
                    modifier = Modifier.padding(bottom = 20.dp),
                    text = content,
                    color = ColorFamilyGray500AndGray900,
                    fontSize = 14.dp,
                    textAlign = TextAlign.Center,
                    lineHeight = 21.dp
                )
                HasButton(
                    text = confirmText,
                    onClick = onConfirm
                )
                dismissText?.let { dismissText ->
                    Box(
                        modifier = Modifier.padding(top = 12.dp),
                    ) {
                        HasText(
                            modifier = Modifier.clickable { onDismiss.invoke() },
                            text = dismissText,
                            color = Gray350,
                            fontSize = 14.dp,
                            fontWeight = HasFontWeight.Medium,
                        )
                    }
                }
            }
            BackHandler(onBack = onDismiss)
        }
    }
}

@DevicePreviews
@Composable
fun ConfirmDialogPreview() {
    HasConfirmDialog(
        isShow = true,
        title = "title",
        content = "content",
        confirmText = "confirm",
        dismissText = "dismiss",
        onConfirm = {},
        onDismiss = {}
    )
}