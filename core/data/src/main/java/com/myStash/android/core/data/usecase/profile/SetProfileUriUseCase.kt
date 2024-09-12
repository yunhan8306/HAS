package com.myStash.android.core.data.usecase.profile

import com.myStash.android.core.data.repository.profile.ProfileRepository
import javax.inject.Inject

class SetProfileUriUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
) {
    suspend operator fun invoke(uri: String) =
        profileRepository.setProfileUri(uri)
}