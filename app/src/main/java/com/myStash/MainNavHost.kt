package com.myStash

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.myStash.common.compose.activityViewModel
import com.myStash.core.model.Tag
import com.myStash.design_system.animation.enterTransitionStart
import com.myStash.design_system.animation.exitTransitionStart
import com.myStash.feature.essential.EssentialRoute
import com.myStash.feature.essential.essentialScreen
import com.myStash.navigation.MainNavType
import org.orbitmvi.orbit.compose.collectAsState

@Composable
fun MainNavHost(
    navHostController: NavHostController,
    onShowTestActivity: () -> Unit
) {
    AnimatedNavHost(
        navController = navHostController,
        startDestination = MainNavType.ESSENTIAL.name
    ) {
        essentialScreen(onShowTestActivity)
        testScreen(MainNavType.TEST2.name)
        testScreen(MainNavType.TEST3.name)
        testScreen(MainNavType.TEST4.name)
    }
}

fun NavGraphBuilder.testScreen(
    route: String
) {
    composable(
        route = route,
        enterTransition = { enterTransitionStart },
        exitTransition = { exitTransitionStart }
    ) { TestRoute(route = route) }
}

@Composable
private fun TestRoute(
    viewModel: TestViewModel = activityViewModel(),
    route: String,
) {
    val state by viewModel.collectAsState()

    TestScreen(route, state.data)
}

@Composable
private fun TestScreen(
    route: String = "test",
    tagList: List<Tag>
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Gray)
    ) {
        Text(text = route, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
        LazyColumn {
            items(tagList) { item ->
                Row {
                    Text(text = item.id.toString())
                    Spacer(modifier = Modifier.weight(1f))
                    Text(text = item.name)
                }
            }
        }
    }
}