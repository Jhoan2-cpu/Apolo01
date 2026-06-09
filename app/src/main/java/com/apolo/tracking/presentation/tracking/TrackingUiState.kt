package com.apolo.tracking.presentation.tracking

import com.apolo.tracking.domain.model.Vehicle

data class TrackingUiState(
    val isLoading: Boolean = false,
    val vehicles: List<Vehicle> = emptyList(),
    val selectedVehicle: Vehicle? = null,
    val errorMessage: String? = null
)
