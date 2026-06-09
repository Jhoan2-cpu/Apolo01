package com.apolo.tracking.domain.usecase

import com.apolo.tracking.domain.model.Vehicle
import com.apolo.tracking.domain.repository.VehicleRepository
import javax.inject.Inject

class GetVehiclesUseCase @Inject constructor(
    private val vehicleRepository: VehicleRepository
) {
    suspend operator fun invoke(): List<Vehicle> = vehicleRepository.getVehicles()
}
