package com.has.android.domain.database.datasource.usecase.profile

import com.has.android.domain.database.datasource.repository.profile.ProfileRepository
import javax.inject.Inject

class SetProfileUriUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
) {
    suspend operator fun invoke(uri: String) =
        profileRepository.setProfileUri(uri)
}