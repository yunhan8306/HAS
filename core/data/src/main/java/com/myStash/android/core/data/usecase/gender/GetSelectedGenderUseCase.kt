package com.myStash.android.core.data.usecase.gender

import com.myStash.android.core.data.repository.gender.GenderRepository
import javax.inject.Inject

class GetSelectedGenderUseCase @Inject constructor(
    private val genderRepository: GenderRepository
) {
    val gender = genderRepository.selectedGender
}