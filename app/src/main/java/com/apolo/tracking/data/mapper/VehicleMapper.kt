package com.apolo.tracking.data.mapper

import com.apolo.tracking.data.remote.dto.VehicleDto
import com.apolo.tracking.domain.model.Vehicle
import com.apolo.tracking.domain.model.VehicleStatus

object VehicleMapper {
    fun toDomain(dto: VehicleDto): Vehicle = Vehicle(
        id        = dto.id ?: 0,
        plate     = dto.plate ?: "",
        speed     = dto.speed ?: "0 km/h",
        latitude  = dto.latitude ?: 0.0,
        longitude = dto.longitude ?: 0.0,
        angle     = dto.angle ?: 0f,
        status    = when (dto.status?.lowercase()) {
            "green" -> VehicleStatus.GREEN
            "red"   -> VehicleStatus.RED
            else    -> VehicleStatus.UNKNOWN
        }
    )
}
