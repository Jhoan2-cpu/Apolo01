package com.apolo.tracking.data.mapper

import com.apolo.tracking.data.remote.dto.NotificationDto
import com.apolo.tracking.domain.model.NotificationItem

object NotificationMapper {
    fun toDomain(dto: NotificationDto): NotificationItem = NotificationItem(
        id            = dto.id ?: 0,
        invoiceNumber = dto.invoiceNumber ?: "",
        status        = dto.status ?: "",
        createdAt     = dto.createdAt ?: ""
    )
}
