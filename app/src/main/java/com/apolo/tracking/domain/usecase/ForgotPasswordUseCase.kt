package com.apolo.tracking.domain.usecase

import com.apolo.tracking.domain.repository.AuthRepository
import javax.inject.Inject

class ForgotPasswordUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(email: String): String =
        authRepository.forgotPassword(email)
}
