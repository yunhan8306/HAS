package com.myStash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.myStash.navigation.MainNavType

@Composable
fun MainNavHost(navHostController: NavHostController) {
    AnimatedNavHost(
        navController = navHostController,
        startDestination = MainNavType.TEST1.name
    ) {
        testScreen(MainNavType.TEST1.name)
        testScreen(MainNavType.TEST2.name)
        testScreen(MainNavType.TEST3.name)
        testScreen(MainNavType.TEST4.name)
    }
}

fun NavGraphBuilder.testScreen(
    route: String,
) {
    composable(
        route = route
    ) { TestRoute(route) }
}

@Composable
private fun TestRoute(
    route: String
) {
    TestScreen(route)
}

@Composable
private fun TestScreen(
    route: String = "test"
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Gray)
    ) {
        Text(text = route)
    }
}

@Preview
@Composable
private fun TimeLinePreview() {
    TestScreen(MainNavType.TEST1.name)
}