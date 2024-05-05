package com.myStash

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.myStash.navigation.MainNavigation
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<TestViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navHostController = rememberAnimatedNavController()

            viewModel.test()

            Scaffold(
                bottomBar = {
                    MainNavigation(navHostController)
                }
            ) { paddingValues ->
                Surface(
                    modifier = Modifier.padding(paddingValues)
                ) {
                    MainNavHost(navHostController)
                }
            }
        }
    }
}