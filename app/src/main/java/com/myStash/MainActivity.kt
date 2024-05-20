package com.myStash

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.myStash.navigation.MainNavigation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navHostController = rememberAnimatedNavController()

            Scaffold(
                bottomBar = {
                    MainNavigation(navHostController)
                }
            ) { paddingValues ->
                Surface(
                    modifier = Modifier.fillMaxSize().padding(paddingValues)
                ) {
                    MainNavHost(
                        navHostController = navHostController,
                    )
                }
            }

//            Box(
//                modifier = Modifier
//                    .size(100.dp)
//                    .background(Color.Red)
//                    .clickable {
////                        viewModel.saveTest()
//                        launchGalleryTestActivity()
//                    }
//            )
        }
    }
}