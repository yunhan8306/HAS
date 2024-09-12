package com.myStash.android.core.data.usecase.profile

import com.myStash.android.core.data.repository.profile.ProfileRepository
import javax.inject.Inject

class GetProfileUriUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
) {
    operator fun invoke() = profileRepository.getProfileUri
}