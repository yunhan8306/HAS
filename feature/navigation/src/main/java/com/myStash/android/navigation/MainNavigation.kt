package com.myStash.android.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import coil.compose.AsyncImage

@Composable
fun MainNavigation(navController: NavHostController) {

    val mainNavList = MainNavType.values().asList()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    BottomAppBar(
        modifier = Modifier.height(56.dp),
        backgroundColor = Color.White
    ) {
        BottomNavigation {
            mainNavList.forEach {
                val selected = it.name == currentRoute
                BottomNavigationItem(
                    modifier = Modifier.background(Color.White),
                    selected = selected,
                    onClick = {
                        navController.navigate(route = it.name) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = {
                        AsyncImage(
                            model = if (selected) it.activeIcon else it.inactiveIcon,
                            contentDescription = it.name
                        )
                    },
                    label = {
                        Text(
                            text = it.name,
                            color = Color.Black
                        )
                    }
                )
            }
        }
    }
}