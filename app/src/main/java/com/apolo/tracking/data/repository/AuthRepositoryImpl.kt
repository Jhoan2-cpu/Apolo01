package com.apolo.tracking.data.repository

import com.apolo.tracking.data.local.SessionManager
import com.apolo.tracking.data.remote.api.AuthApi
import com.apolo.tracking.data.remote.dto.ForgotPasswordRequestDto
import com.apolo.tracking.data.remote.dto.LoginRequestDto
import com.apolo.tracking.domain.model.ApiException
import com.apolo.tracking.domain.model.AuthToken
import com.apolo.tracking.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authApi: AuthApi,
    private val sessionManager: SessionManager
) : AuthRepository {

    override suspend fun login(email: String, password: String): AuthToken {
        try {
            val response = authApi.login(LoginRequestDto(email, password))
            if (!response.success || response.data == null) throw Exception(response.message)
            val token = AuthToken(
                tokenValue = response.data.tokenValue,
                tokenExpired = response.data.tokenExpired
            )
            sessionManager.saveToken(token.tokenValue, token.tokenExpired)
            return token
        } catch (e: HttpException) {
            throw ApiException(e.code(), e.message())
        }
    }

    override suspend fun forgotPassword(email: String): String {
        try {
            val response = authApi.forgotPassword(ForgotPasswordRequestDto(email))
            if (!response.success) throw Exception(response.message)
            return response.message
        } catch (e: HttpException) {
            throw ApiException(e.code(), e.message())
        }
    }

    override fun isSessionActive(): Flow<Boolean> = sessionManager.isSessionActive

    override suspend fun logout() = sessionManager.clearSession()
}
