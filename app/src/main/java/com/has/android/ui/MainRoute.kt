package com.has.android.ui

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
import com.has.android.MainViewModel
import com.has.android.common.util.CommonActivityResultContract
import com.has.android.design_system.animation.slideIn
import com.has.android.design_system.ui.theme.HasDefaultTheme
import com.has.android.feature.item.ItemActivity
import com.has.android.common.util.constants.ItemConstants
import com.has.android.feature.item.item.getItemTab
import com.has.android.navigation.HasFloatingButton
import com.has.android.navigation.MainNavHost
import com.has.android.navigation.MainNavType
import com.has.android.navigation.MainNavigation
import com.has.android.navigation.navigate
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
        onResult = { intent ->
            intent?.getStringExtra(ItemConstants.CMD_COMPLETE)?.let { cmd ->
                val navType = when(cmd) {
                    ItemConstants.CMD_HAS -> MainNavType.HAS
                    ItemConstants.CMD_STYLE -> MainNavType.STYLE
                    ItemConstants.CMD_FEED -> MainNavType.FEED
                    else -> MainNavType.HAS
                }
                navHostController.navigate(navType)
            }
        }
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
                            activity.slideIn()
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