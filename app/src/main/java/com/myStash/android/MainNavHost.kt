package com.myStash.android

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
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.myStash.android.common.compose.activityViewModel
import com.myStash.android.core.model.Tag
import com.myStash.android.feature.essential.hasScreen
import com.myStash.android.feature.feed.feedScreen
import com.myStash.android.feature.myPage.myPageScreen
import com.myStash.android.navigation.MainNavType
import org.orbitmvi.orbit.compose.collectAsState

@Composable
fun MainNavHost(
    navHostController: NavHostController,
) {
    AnimatedNavHost(
        navController = navHostController,
        startDestination = MainNavType.ESSENTIAL.name
    ) {
        hasScreen()
        testScreen(MainNavType.TEST2.name)
        feedScreen()
        myPageScreen()
    }
}

fun NavGraphBuilder.testScreen(
    route: String
) {
    composable(
        route = route
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