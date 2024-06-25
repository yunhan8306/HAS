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
fun ConfirmDialog(
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
                Text(
                    modifier = Modifier.padding(bottom = 12.dp),
                    text = title,
                    style = TextStyle(
                        fontSize = 15.sp,
                        lineHeight = 22.5.sp,
                        fontWeight = FontWeight(500),
                        color = Color(0xFF202020),
                        textAlign = TextAlign.Center,
                    )
                )
                Text(
                    modifier = Modifier.padding(bottom = 20.dp),
                    text = content,
                    style = TextStyle(
                        fontSize = 14.sp,
                        lineHeight = 21.sp,
                        fontWeight = FontWeight(500),
                        color = Color(0xFF505050),
                        textAlign = TextAlign.Center,
                    )
                )
                Box(
                    modifier = Modifier
                        .width(296.dp)
                        .height(48.dp)
                        .background(
                            color = Color(0xFF202020),
                            shape = RoundedCornerShape(size = 10.dp)
                        )
                        .clickable { onConfirm.invoke() },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = confirmText,
                        style = TextStyle(
                            fontSize = 16.sp,
                            lineHeight = 16.sp,
                            fontWeight = FontWeight(700),
                            color = Color(0xFFE4F562),
                            textAlign = TextAlign.Center,
                        )
                    )
                }
                dismissText?.let {
                    Box(
                        modifier = Modifier.padding(top = 12.dp),
                    ) {
                        Text(
                            modifier = Modifier.clickable { onDismiss.invoke() },
                            text = it,
                            style = TextStyle(
                                fontSize = 14.sp,
                                lineHeight = 20.sp,
                                fontWeight = FontWeight(500),
                                color = Color(0xFF909090),
                                textAlign = TextAlign.Center,
                            )
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
    ConfirmDialog(
        isShow = true,
        title = "title",
        content = "content",
        confirmText = "confirm",
        dismissText = "dismiss",
        onConfirm = {},
        onDismiss = {}
    )
}