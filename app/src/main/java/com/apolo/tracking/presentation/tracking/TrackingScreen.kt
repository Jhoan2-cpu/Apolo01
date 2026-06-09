package com.apolo.tracking.presentation.tracking

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.apolo.tracking.R
import com.apolo.tracking.domain.model.Vehicle
import com.apolo.tracking.domain.model.VehicleStatus
import com.apolo.tracking.presentation.components.ErrorContent
import com.apolo.tracking.ui.theme.BluePrimary
import com.apolo.tracking.ui.theme.TextSecondary
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MarkerComposable
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun TrackingScreen(
    viewModel: TrackingViewModel = hiltViewModel(),
    onLogout: () -> Unit = {}
) {
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
        TrackingTopBar(onLogout = onLogout)
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
                            val statusColor = vehicle.statusColor
                            val iconRes = when (vehicle.status) {
                                VehicleStatus.RED -> R.drawable.ic_vehicle_red
                                else              -> R.drawable.ic_vehicle_green
                            }
                            MarkerComposable(
                                keys = arrayOf(vehicle),
                                state = MarkerState(
                                    position = LatLng(vehicle.latitude, vehicle.longitude)
                                ),
                                anchor = Offset(0.5f, 0.3f),
                                onClick = {
                                    viewModel.selectVehicle(vehicle)
                                    false
                                }
                            ) {
                                VehicleMarkerContent(
                                    plate = vehicle.plate,
                                    speed = vehicle.speed,
                                    angle = vehicle.angle,
                                    statusColor = statusColor,
                                    iconRes = iconRes
                                )
                            }
                        }
                    }

                    if (uiState.vehicles.isEmpty()) {
                        Card(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .padding(horizontal = 16.dp, vertical = 12.dp)
                                .fillMaxWidth(),
                            shape = RoundedCornerShape(20.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White)
                        ) {
                            Text(
                                text = "No hay vehículos disponibles",
                                color = TextSecondary,
                                fontSize = 14.sp,
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    } else {
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
}

@Composable
private fun VehicleMarkerContent(
    plate: String,
    speed: String,
    angle: Float,
    statusColor: Color,
    iconRes: Int
) {
    Column(
        modifier = Modifier.wrapContentSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = null,
            modifier = Modifier
                .size(64.dp)
                // PNG points DOWN; -90 turns it RIGHT, then -angle applies heading
                .rotate(-90f - angle)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Box(
            modifier = Modifier
                .background(Color.White, RoundedCornerShape(50.dp))
                .border(2.dp, statusColor, RoundedCornerShape(50.dp))
                .padding(horizontal = 12.dp, vertical = 5.dp)
        ) {
            Text(
                text = plate,
                color = statusColor,
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp
            )
        }
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = speed,
            color = statusColor,
            fontWeight = FontWeight.Medium,
            fontSize = 11.sp
        )
    }
}

@Composable
private fun TrackingTopBar(onLogout: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(BluePrimary)
            .padding(horizontal = 4.dp, vertical = 10.dp)
    ) {
        Text(
            text = "Monitorea tu Envío",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            modifier = Modifier.align(Alignment.Center)
        )
        IconButton(
            onClick = onLogout,
            modifier = Modifier.align(Alignment.CenterEnd)
        ) {
            Icon(imageVector = Icons.Filled.Logout, contentDescription = "Cerrar sesión", tint = Color.White)
        }
    }
}

@Composable
private fun VehicleInfoCard(vehicle: Vehicle?, modifier: Modifier = Modifier) {
    val statusColor = vehicle?.statusColor ?: Color(0xFF9E9E9E)
    val statusLabel = when (vehicle?.status) {
        VehicleStatus.GREEN -> "En Tránsito"
        VehicleStatus.RED   -> "Detenido"
        else                -> "Desconocido"
    }

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
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
                    .size(44.dp)
                    .background(statusColor.copy(alpha = 0.12f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.DirectionsCar,
                    contentDescription = null,
                    tint = statusColor,
                    modifier = Modifier.size(24.dp)
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = vehicle?.plate ?: "—",
                    fontWeight = FontWeight.Bold,
                    color = BluePrimary,
                    fontSize = 17.sp
                )
                Text(text = vehicle?.speed ?: "—", color = TextSecondary, fontSize = 13.sp)
            }
            Box(
                modifier = Modifier
                    .background(statusColor.copy(alpha = 0.12f), RoundedCornerShape(20.dp))
                    .padding(horizontal = 10.dp, vertical = 5.dp)
            ) {
                Text(
                    text = statusLabel,
                    color = statusColor,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 12.sp
                )
            }
        }
    }
}

private val Vehicle.statusColor: Color
    get() = when (status) {
        VehicleStatus.GREEN -> Color(0xFF4CAF50)
        VehicleStatus.RED   -> Color(0xFFF44336)
        else                -> Color(0xFF9E9E9E)
    }
