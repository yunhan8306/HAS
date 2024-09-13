package com.myStash.android.feature.style.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.myStash.android.common.resource.R
import com.myStash.android.design_system.ui.color.ColorFamilyGray500AndGray900
import com.myStash.android.design_system.ui.component.text.HasText

@Composable
fun StyleEmptyScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Image(
            painter = painterResource(id = if(isSystemInDarkTheme()) R.drawable.img_hadung_empty_dark else R.drawable.img_hadung_empty_light),
            contentDescription = "style empty"
        )
        HasText(
            modifier = Modifier.padding(top = 16.dp),
            text = "There is no registered Style.",
            fontSize = 14.dp,
            color = ColorFamilyGray500AndGray900

        )
        Spacer(modifier = Modifier.weight(1f))
    }
}