package com.apolo.tracking.domain.usecase

import com.apolo.tracking.domain.model.AuthToken
import com.apolo.tracking.domain.repository.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): AuthToken =
        authRepository.login(email, password)
}
