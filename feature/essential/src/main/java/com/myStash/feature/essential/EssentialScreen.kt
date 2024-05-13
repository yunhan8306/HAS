package com.myStash.feature.essential

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable
import com.myStash.design_system.animation.enterTransitionStart
import com.myStash.design_system.animation.exitTransitionStart
import com.myStash.navigation.MainNavType

fun NavGraphBuilder.essentialScreen(
    onShowTestActivity: () -> Unit
) {

    composable(
        route = MainNavType.ESSENTIAL.name,
        enterTransition = { enterTransitionStart },
        exitTransition = { exitTransitionStart }
    ) {
        EssentialRoute(onShowTestActivity = onShowTestActivity)
    }
}

@Composable
fun EssentialRoute(
    viewModel: EssentialViewModel = hiltViewModel(),
    onShowTestActivity: () -> Unit
) {
    EssentialScreen(
        onClick = viewModel::onClick,
        onShowTestActivity = onShowTestActivity
    )
}

@Composable
fun EssentialScreen(
    onClick: () -> Unit,
    onShowTestActivity: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Blue)
    ) {
        Text(text = "Essential Screen",
            modifier = Modifier.size(50.dp).background(Color.Red).clickable {
                onClick.invoke()
                onShowTestActivity.invoke()
            })
    }
}