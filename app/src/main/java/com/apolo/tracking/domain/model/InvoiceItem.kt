package com.apolo.tracking.domain.model

data class InvoiceItem(
    val id: Int,
    val invoiceNumber: String,
    val amount: Double,
    val status: String,
    val date: String
)
