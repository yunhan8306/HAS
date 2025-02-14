package com.has.android.domain.database.datasource.usecase.gender

import com.has.android.domain.database.datasource.repository.gender.GenderRepository
import javax.inject.Inject

class GetSelectedGenderUseCase @Inject constructor(
    private val genderRepository: GenderRepository
) {
    val gender = genderRepository.selectedGender
}