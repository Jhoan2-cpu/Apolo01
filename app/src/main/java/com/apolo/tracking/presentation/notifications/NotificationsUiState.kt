package com.apolo.tracking.presentation.notifications

import com.apolo.tracking.domain.model.NotificationItem

data class NotificationsUiState(
    val isLoading: Boolean = false,
    val notifications: List<NotificationItem> = emptyList(),
    val errorMessage: String? = null
)
