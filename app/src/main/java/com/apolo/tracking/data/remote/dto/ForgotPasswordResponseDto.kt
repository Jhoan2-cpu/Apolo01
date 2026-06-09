package com.apolo.tracking.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ForgotPasswordResponseDto(
    @SerializedName("success") val success: Boolean,
    @SerializedName("message") val message: String
)
