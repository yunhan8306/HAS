package com.myStash

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.myStash.core.model.Tag
import com.myStash.navigation.MainNavType

@Composable
fun MainNavHost(navHostController: NavHostController, tagList: List<Tag>) {
    AnimatedNavHost(
        navController = navHostController,
        startDestination = MainNavType.TEST1.name
    ) {
        testScreen(MainNavType.TEST1.name, tagList)
        testScreen(MainNavType.TEST2.name, tagList)
        testScreen(MainNavType.TEST3.name, tagList)
        testScreen(MainNavType.TEST4.name, tagList)
    }
}

fun NavGraphBuilder.testScreen(
    route: String,
    tagList: List<Tag>
) {
    composable(
        route = route
    ) { TestRoute(route, tagList) }
}

@Composable
private fun TestRoute(
    route: String,
    tagList: List<Tag>
) {
    TestScreen(route, tagList)
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