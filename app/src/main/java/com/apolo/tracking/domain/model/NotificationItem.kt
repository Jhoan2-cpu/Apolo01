package com.apolo.tracking.domain.model

data class NotificationItem(
    val id: Int,
    val invoiceNumber: String,
    val status: String,
    val createdAt: String
)
