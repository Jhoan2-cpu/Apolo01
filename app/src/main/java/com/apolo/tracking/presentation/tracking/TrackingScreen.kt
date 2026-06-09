package com.apolo.tracking.presentation.tracking

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.apolo.tracking.domain.model.Vehicle
import com.apolo.tracking.domain.model.VehicleStatus
import com.apolo.tracking.presentation.components.AppTopBar
import com.apolo.tracking.presentation.components.ErrorContent
import com.apolo.tracking.ui.theme.BluePrimary
import com.apolo.tracking.ui.theme.CyanSecondary
import com.apolo.tracking.ui.theme.TextSecondary
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun TrackingScreen(viewModel: TrackingViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val defaultPosition = LatLng(-12.059482, -77.032028)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(defaultPosition, 13f)
    }

    LaunchedEffect(uiState.vehicles) {
        if (uiState.vehicles.isNotEmpty()) {
            val first = uiState.vehicles.first()
            cameraPositionState.animate(
                CameraUpdateFactory.newLatLngZoom(
                    LatLng(first.latitude, first.longitude), 13f
                )
            )
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        AppTopBar(title = "Monitorea tu Envío", onActionClick = {})
        Box(modifier = Modifier.fillMaxSize()) {
            when {
                uiState.isLoading -> Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = BluePrimary)
                }
                uiState.errorMessage != null -> ErrorContent(
                    message = uiState.errorMessage!!,
                    onRetry = viewModel::loadVehicles
                )
                else -> {
                    GoogleMap(
                        modifier = Modifier.fillMaxSize(),
                        cameraPositionState = cameraPositionState
                    ) {
                        uiState.vehicles.forEach { vehicle ->
                            Marker(
                                state = MarkerState(
                                    position = LatLng(vehicle.latitude, vehicle.longitude)
                                ),
                                title = vehicle.plate,
                                snippet = vehicle.speed,
                                rotation = vehicle.angle,
                                onClick = {
                                    viewModel.selectVehicle(vehicle)
                                    false
                                }
                            )
                        }
                    }
                    VehicleInfoCard(
                        vehicle = uiState.selectedVehicle,
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(horizontal = 16.dp, vertical = 12.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun VehicleInfoCard(vehicle: Vehicle?, modifier: Modifier = Modifier) {
    val statusColor = when (vehicle?.status) {
        VehicleStatus.GREEN -> Color(0xFF4CAF50)
        VehicleStatus.RED -> Color(0xFFF44336)
        else -> Color(0xFF9E9E9E)
    }

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(12.dp)
                    .background(statusColor, CircleShape)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = vehicle?.plate ?: "—",
                    fontWeight = FontWeight.Bold,
                    color = BluePrimary,
                    fontSize = 16.sp
                )
                Text(
                    text = vehicle?.speed ?: "—",
                    color = TextSecondary,
                    fontSize = 13.sp
                )
            }
            Text(
                text = when (vehicle?.status) {
                    VehicleStatus.GREEN -> "En Tránsito"
                    VehicleStatus.RED -> "Detenido"
                    else -> "Desconocido"
                },
                color = CyanSecondary,
                fontWeight = FontWeight.SemiBold,
                fontSize = 13.sp
            )
        }
    }
}
