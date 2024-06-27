package com.myStash.android.design_system.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun HasConfirmDialog(
    isShow: Boolean,
    title: String,
    content: String,
    confirmText: String,
    dismissText: String? = null,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
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
                    .shadow(
                        elevation = 6.dp,
                        spotColor = Color(0x29000000),
                        ambientColor = Color(0x29000000)
                    )
                    .width(328.dp)
                    .background(color = Color(0xFFFFFFFF), shape = RoundedCornerShape(size = 10.dp))
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
                    color = Color(0xFF505050),
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
                            color = Color(0xFF909090),
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