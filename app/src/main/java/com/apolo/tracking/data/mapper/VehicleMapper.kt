package com.apolo.tracking.data.mapper

import com.apolo.tracking.data.remote.dto.VehicleDto
import com.apolo.tracking.domain.model.Vehicle
import com.apolo.tracking.domain.model.VehicleStatus

object VehicleMapper {
    fun toDomain(dto: VehicleDto): Vehicle = Vehicle(
        id = dto.id,
        plate = dto.plate,
        speed = dto.speed,
        latitude = dto.latitude,
        longitude = dto.longitude,
        angle = dto.angle,
        status = when (dto.status.lowercase()) {
            "green" -> VehicleStatus.GREEN
            "red" -> VehicleStatus.RED
            else -> VehicleStatus.UNKNOWN
        }
    )
}
