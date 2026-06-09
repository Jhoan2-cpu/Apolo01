package com.apolo.tracking.domain.model

data class Vehicle(
    val id: Int,
    val plate: String,
    val speed: String,
    val latitude: Double,
    val longitude: Double,
    val angle: Float,
    val status: VehicleStatus
)
