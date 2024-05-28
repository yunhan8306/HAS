package com.myStash.android.feature.gender

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class GenderViewModel @Inject constructor(

): ViewModel() {

    private val _selectedGender = MutableStateFlow(GenderType.WOMEN)
    val selectedGender = _selectedGender.asStateFlow()

    fun selectGender(genderType: GenderType) {
        _selectedGender.value = genderType
    }
}

enum class GenderType {
    MEN, WOMEN
}