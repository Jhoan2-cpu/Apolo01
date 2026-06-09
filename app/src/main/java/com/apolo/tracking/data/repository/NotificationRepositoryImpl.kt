package com.apolo.tracking.data.repository

import com.apolo.tracking.data.mapper.NotificationMapper
import com.apolo.tracking.data.remote.api.NotificationApi
import com.apolo.tracking.domain.model.NotificationItem
import com.apolo.tracking.domain.repository.NotificationRepository
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor(
    private val notificationApi: NotificationApi
) : NotificationRepository {

    override suspend fun getNotifications(): List<NotificationItem> {
        val response = notificationApi.getNotifications()
        if (!response.success || response.data == null) throw Exception(response.message)
        return response.data.map { NotificationMapper.toDomain(it) }
    }
}
