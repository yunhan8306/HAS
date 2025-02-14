package com.has.android.core.data.usecase.profile

import com.has.android.core.data.repository.profile.ProfileRepository
import javax.inject.Inject

class SetNickNameUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
) {
    suspend operator fun invoke(nickName: String) =
        profileRepository.setNickName(nickName)
}