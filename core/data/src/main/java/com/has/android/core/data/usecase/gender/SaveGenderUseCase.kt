package com.has.android.core.data.usecase.gender

import com.has.android.core.data.repository.gender.GenderRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SaveGenderUseCase @Inject constructor(
    private val genderRepository: GenderRepository
) {
    suspend operator fun invoke(gender: String) {
        genderRepository.selectGender(gender)
    }
}