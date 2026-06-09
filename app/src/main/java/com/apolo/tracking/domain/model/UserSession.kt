package com.apolo.tracking.domain.model

data class UserSession(
    val token: AuthToken,
    val isActive: Boolean
)
