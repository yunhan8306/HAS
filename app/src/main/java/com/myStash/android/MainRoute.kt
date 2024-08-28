package com.myStash.android

import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FabPosition
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.myStash.android.common.util.CommonActivityResultContract
import com.myStash.android.design_system.ui.theme.HasDefaultTheme
import com.myStash.android.feature.item.ItemActivity
import com.myStash.android.feature.item.ItemConstants
import com.myStash.android.feature.item.item.getItemTab
import com.myStash.android.navigation.HasFloatingButton
import com.myStash.android.navigation.MainNavigation
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun MainRoute(
    viewModel: MainViewModel = hiltViewModel()
) {
    val navHostController = rememberAnimatedNavController()
    val navBackStackEntry by navHostController.currentBackStackEntryAsState()

    val activity = LocalContext.current as ComponentActivity
    val itemActivityLauncher = rememberLauncherForActivityResult(
        contract = CommonActivityResultContract(),
        onResult = {}
    )

    viewModel.collectSideEffect { sideEffect ->
        when(sideEffect) {
            else -> Unit
        }
    }

    HasDefaultTheme {
        Scaffold(
            bottomBar = {
                MainNavigation(navHostController)
            },
            floatingActionButton = {
                HasFloatingButton(
                    onClick = {
                        Intent(activity, ItemActivity::class.java).apply {
                            putExtra(ItemConstants.CMD_TAB_NAME, navBackStackEntry?.destination?.route.getItemTab().name)
                            itemActivityLauncher.launch(this)
                        }
                    }
                )
            },
            floatingActionButtonPosition = FabPosition.Center,
            isFloatingActionButtonDocked = true
        ) { paddingValues ->
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                MainNavHost(
                    navHostController = navHostController,
                )
            }
        }

        MainBottomModalRoute(
            navHostController = navHostController,
        )
    }
}