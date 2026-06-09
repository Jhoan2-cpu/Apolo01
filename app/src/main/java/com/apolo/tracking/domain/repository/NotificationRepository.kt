package com.apolo.tracking.domain.repository

import com.apolo.tracking.domain.model.NotificationItem

interface NotificationRepository {
    suspend fun getNotifications(): List<NotificationItem>
}
