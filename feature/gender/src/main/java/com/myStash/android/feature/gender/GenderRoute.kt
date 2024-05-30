package com.myStash.android.feature.gender

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun GenderRoute(
    viewModel: GenderViewModel = hiltViewModel()
) {

    val selectGenderType = viewModel.collectAsState().value.gender
    val activity = LocalContext.current as ComponentActivity

    viewModel.collectSideEffect { sideEffect ->
        when(sideEffect) {
            is GenderSideEffect.Finish -> activity.finish()
        }
    }

    GenderScreen(
        selectGenderType = selectGenderType,
        selectGender = viewModel::selectGender,
        saveGender = viewModel::saveGender
    )
}