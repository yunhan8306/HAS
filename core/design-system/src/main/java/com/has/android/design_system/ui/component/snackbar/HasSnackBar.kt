package com.has.android.design_system.ui.component.snackbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.has.android.design_system.ui.component.text.HasText

@Composable
fun HasSnackBar(
    state: SnackbarHostState
) {
    SnackbarHost(
        modifier = Modifier,
        hostState = state,
        snackbar = { data ->
            Box(
                modifier = Modifier
                    .background(color = Color(0xFF85867C), shape = RoundedCornerShape(size = 25.dp))
                    .padding(start = 24.dp, top = 14.dp, end = 24.dp, bottom = 15.dp),
                contentAlignment = Alignment.Center
            ) {
                HasText(
                    text = data.message,
                    color = Color.White
                )
            }
        }
    )
}