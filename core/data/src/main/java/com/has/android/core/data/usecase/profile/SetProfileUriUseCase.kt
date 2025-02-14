package com.has.android.core.data.usecase.profile

import com.has.android.core.data.repository.profile.ProfileRepository
import javax.inject.Inject

class SetProfileUriUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
) {
    suspend operator fun invoke(uri: String) =
        profileRepository.setProfileUri(uri)
}