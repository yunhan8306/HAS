package com.myStash.android

import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.myStash.android.common.util.CommonActivityResultContract
import com.myStash.android.design_system.animation.slideIn
import com.myStash.android.design_system.ui.theme.HasDefaultTheme
import com.myStash.android.feature.gender.GenderActivity
import com.myStash.android.feature.item.ItemActivity
import com.myStash.android.feature.item.item.ItemTab
import com.myStash.android.navigation.MainNavigation
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun MainRoute(
    viewModel: MainViewModel = hiltViewModel()
) {
    val navHostController = rememberAnimatedNavController()

    val activity = LocalContext.current as ComponentActivity
    val itemActivityLauncher = rememberLauncherForActivityResult(
        contract = CommonActivityResultContract(),
        onResult = {}
    )

    viewModel.collectSideEffect { sideEffect ->
        when(sideEffect) {
            is MainSideEffect.StartGenderActivity -> {
                val intent = Intent(activity, GenderActivity::class.java)
                itemActivityLauncher.launch(intent)
            }
        }
    }

    HasDefaultTheme {
        Scaffold(
            bottomBar = {
                MainNavigation(navHostController)
            }
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

        MainBottomModal(
            navHostController = navHostController,
            showAddStyleItemActivity = { hasList ->
                val intent = Intent(activity, ItemActivity::class.java).apply {
                    putExtra("tab", ItemTab.STYLE.name)
                    putExtra("style", hasList.map { it.id }.toTypedArray())
                }
                itemActivityLauncher.launch(intent)
                activity.slideIn()
            },
            showAddFeedItemActivity = { style ->
                val intent = Intent(activity, ItemActivity::class.java).apply {
                    putExtra("tab", ItemTab.FEED.name)
                    putExtra(ItemTab.FEED.name, style.id)
                }
                itemActivityLauncher.launch(intent)
                activity.slideIn()
            }
        )
    }
}