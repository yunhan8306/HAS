package com.has.android.domain.database.datasource.usecase.profile

import com.has.android.domain.database.datasource.repository.profile.ProfileRepository
import javax.inject.Inject

class GetNickNameUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
) {
    operator fun invoke() = profileRepository.getNickName
}