package com.has.android.domain.database.datasource.usecase.gender

import com.has.android.domain.database.datasource.repository.gender.GenderRepository
import javax.inject.Inject

class SaveGenderUseCase @Inject constructor(
    private val genderRepository: GenderRepository
) {
    suspend operator fun invoke(gender: String) {
        genderRepository.selectGender(gender)
    }
}