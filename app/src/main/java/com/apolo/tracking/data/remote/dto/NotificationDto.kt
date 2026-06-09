package com.apolo.tracking.data.remote.dto

import com.google.gson.annotations.SerializedName

data class NotificationDto(
    @SerializedName("id")             val id: Int?,
    @SerializedName("invoice_number") val invoiceNumber: String?,
    @SerializedName("status")         val status: String?,
    @SerializedName("created_at")     val createdAt: String?
)
