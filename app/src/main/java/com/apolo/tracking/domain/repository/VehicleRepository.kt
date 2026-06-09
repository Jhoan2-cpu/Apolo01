package com.apolo.tracking.domain.repository

import com.apolo.tracking.domain.model.Vehicle

interface VehicleRepository {
    suspend fun getVehicles(): List<Vehicle>
}
