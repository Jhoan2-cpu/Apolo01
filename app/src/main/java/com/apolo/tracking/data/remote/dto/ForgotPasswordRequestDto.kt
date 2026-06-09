package com.apolo.tracking.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ForgotPasswordRequestDto(
    @SerializedName("email") val email: String
)
