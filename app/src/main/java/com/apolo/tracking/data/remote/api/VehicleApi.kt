package com.apolo.tracking.data.remote.api

import com.apolo.tracking.data.remote.dto.ApiResponse
import com.apolo.tracking.data.remote.dto.VehicleDto
import retrofit2.http.GET

interface VehicleApi {
    @GET("vehicles")
    suspend fun getVehicles(): ApiResponse<List<VehicleDto>>
}
