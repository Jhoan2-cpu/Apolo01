package com.apolo.tracking.domain.repository

import com.apolo.tracking.domain.model.AuthToken
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun login(email: String, password: String): AuthToken
    suspend fun forgotPassword(email: String): String
    fun isSessionActive(): Flow<Boolean>
    suspend fun logout()
}
