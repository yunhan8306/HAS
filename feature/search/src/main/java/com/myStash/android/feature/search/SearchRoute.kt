package com.myStash.android.feature.search

import android.app.Activity
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun SearchRoute(
    viewModel: SearchViewModel = hiltViewModel()
) {

    val activity = LocalContext.current as ComponentActivity
    val state by viewModel.collectAsState()

    viewModel.collectSideEffect { sideEffect ->
        when(sideEffect) {
            is SearchSideEffect.Complete -> {
                activity.apply {
                    setResult(Activity.RESULT_OK, sideEffect.intent)
                    finish()
                }
            }
        }
    }

    SearchScreen(
        searchTextState = viewModel.searchTextState,
        state = state,
        onAction = { action ->
            when(action) {

                else -> viewModel.onAction(action)
            }
        },
    )

    BackHandler {
        viewModel.onAction(SearchAction.Confirm)
    }
}