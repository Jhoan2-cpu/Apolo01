package com.apolo.tracking.data.remote.api

import com.apolo.tracking.data.remote.dto.ApiResponse
import com.apolo.tracking.data.remote.dto.ForgotPasswordRequestDto
import com.apolo.tracking.data.remote.dto.ForgotPasswordResponseDto
import com.apolo.tracking.data.remote.dto.LoginDataDto
import com.apolo.tracking.data.remote.dto.LoginRequestDto
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequestDto): ApiResponse<LoginDataDto>

    @POST("auth/forgot")
    suspend fun forgotPassword(@Body request: ForgotPasswordRequestDto): ForgotPasswordResponseDto
}
