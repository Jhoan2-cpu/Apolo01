package com.apolo.tracking.data.repository

import com.apolo.tracking.data.mapper.VehicleMapper
import com.apolo.tracking.data.remote.api.VehicleApi
import com.apolo.tracking.domain.model.Vehicle
import com.apolo.tracking.domain.repository.VehicleRepository
import javax.inject.Inject

class VehicleRepositoryImpl @Inject constructor(
    private val vehicleApi: VehicleApi
) : VehicleRepository {

    override suspend fun getVehicles(): List<Vehicle> {
        val response = vehicleApi.getVehicles()
        if (!response.success || response.data == null) {
            throw Exception(response.message)
        }
        return response.data.map { VehicleMapper.toDomain(it) }
    }
}
