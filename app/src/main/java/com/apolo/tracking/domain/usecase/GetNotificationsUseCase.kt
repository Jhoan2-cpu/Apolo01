package com.apolo.tracking.domain.usecase

import com.apolo.tracking.domain.model.NotificationItem
import com.apolo.tracking.domain.repository.NotificationRepository
import javax.inject.Inject

class GetNotificationsUseCase @Inject constructor(
    private val notificationRepository: NotificationRepository
) {
    suspend operator fun invoke(): List<NotificationItem> = notificationRepository.getNotifications()
}
