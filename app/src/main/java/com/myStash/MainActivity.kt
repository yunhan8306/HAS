package com.myStash

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.myStash.navigation.MainNavigation
import dagger.hilt.android.AndroidEntryPoint
import org.orbitmvi.orbit.compose.collectAsState

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<TestViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navHostController = rememberAnimatedNavController()
            val state by viewModel.collectAsState()

            Scaffold(
                bottomBar = {
                    MainNavigation(navHostController)
                }
            ) { paddingValues ->
                Surface(
                    modifier = Modifier.padding(paddingValues)
                ) {
                    MainNavHost(navHostController, state.data)
                }
            }

            Box(
                modifier = Modifier
                    .size(100.dp)
                    .background(Color.Red)
                    .clickable {
                        viewModel.saveTest()
                    }
            )
        }
    }
}