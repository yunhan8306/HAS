package com.has.android.domain.database.datasource.usecase.profile

import com.has.android.domain.database.datasource.repository.profile.ProfileRepository
import javax.inject.Inject

class SetNickNameUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
) {
    suspend operator fun invoke(nickName: String) =
        profileRepository.setNickName(nickName)
}