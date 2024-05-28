package com.myStash.android.feature.gender

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun GenderRoute(
    viewModel: GenderViewModel = hiltViewModel()
) {

    val selectGenderType = viewModel.selectedGender.collectAsState().value

    GenderScreen(
        selectGenderType = selectGenderType,
        selectGender = viewModel::selectGender
    )
}