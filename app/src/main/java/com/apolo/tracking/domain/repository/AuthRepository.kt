package com.apolo.tracking.domain.repository

import com.apolo.tracking.domain.model.AuthToken

interface AuthRepository {
    suspend fun login(email: String, password: String): AuthToken
    suspend fun forgotPassword(email: String): String
}
