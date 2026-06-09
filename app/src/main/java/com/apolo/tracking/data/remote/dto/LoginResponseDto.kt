package com.apolo.tracking.data.remote.dto

import com.google.gson.annotations.SerializedName

data class LoginResponseDto(
    @SerializedName("token_value") val tokenValue: String,
    @SerializedName("token_expired") val tokenExpired: String
)
