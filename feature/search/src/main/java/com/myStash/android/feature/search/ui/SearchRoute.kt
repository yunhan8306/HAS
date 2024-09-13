package com.myStash.android.feature.search.ui

import android.app.Activity
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.myStash.android.feature.search.SearchViewModel
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
                is SearchAction.Finish -> {
                    activity.finish()
                }
                else -> viewModel.onAction(action)
            }
        },
    )

    BackHandler {
        viewModel.onAction(SearchAction.Confirm)
    }
}