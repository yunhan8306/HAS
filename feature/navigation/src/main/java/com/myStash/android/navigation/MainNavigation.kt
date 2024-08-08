package com.myStash.android.navigation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import coil.compose.AsyncImage
import com.myStash.android.design_system.ui.color.ColorFamilyWhiteAndGray600

@Composable
fun MainNavigation(navController: NavHostController) {
    val mainNavList = MainNavType.entries
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    BottomAppBar(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .shadow(elevation = 10.dp, shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
        backgroundColor = ColorFamilyWhiteAndGray600
    ) {
        BottomNavigation(
            elevation = 0.dp,
            backgroundColor = ColorFamilyWhiteAndGray600
        ) {
            mainNavList.forEach {
                val selected = it.name == currentRoute
                BottomNavigationItem(
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