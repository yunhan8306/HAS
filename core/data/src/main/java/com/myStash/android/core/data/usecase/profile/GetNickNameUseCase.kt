package com.myStash.android.core.data.usecase.profile

import com.myStash.android.core.data.repository.profile.ProfileRepository
import javax.inject.Inject

class GetNickNameUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
) {
    operator fun invoke() = profileRepository.getNickName
}