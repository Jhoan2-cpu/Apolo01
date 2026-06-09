package com.apolo.tracking.data.remote.api

import com.apolo.tracking.data.remote.dto.NotificationDto
import retrofit2.http.GET

interface NotificationApi {
    @GET("notifications")
    suspend fun getNotifications(): List<NotificationDto>
}
