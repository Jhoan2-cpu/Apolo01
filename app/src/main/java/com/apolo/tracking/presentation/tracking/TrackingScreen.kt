package com.apolo.tracking.presentation.tracking

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
import com.apolo.tracking.ui.theme.CyanSecondary
import com.apolo.tracking.ui.theme.TextHint
import com.apolo.tracking.ui.theme.TextSecondary
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapEffect
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun TrackingScreen(
    viewModel: TrackingViewModel = hiltViewModel(),
    onNavigateToNotifications: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    var greenIcon by remember { mutableStateOf<BitmapDescriptor?>(null) }
    var redIcon by remember { mutableStateOf<BitmapDescriptor?>(null) }

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
        TrackingTopBar(onNotificationsClick = onNavigateToNotifications)
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
                        MapEffect(Unit) { _ ->
                            val density = context.resources.displayMetrics.density
                            greenIcon = vehicleIcon(context, R.drawable.ic_vehicle_green, density)
                            redIcon = vehicleIcon(context, R.drawable.ic_vehicle_red, density)
                        }

                        uiState.vehicles.forEach { vehicle ->
                            Marker(
                                state = MarkerState(
                                    position = LatLng(vehicle.latitude, vehicle.longitude)
                                ),
                                icon = when (vehicle.status) {
                                    VehicleStatus.GREEN -> greenIcon
                                    VehicleStatus.RED -> redIcon
                                    VehicleStatus.UNKNOWN -> greenIcon
                                },
                                title = vehicle.plate,
                                snippet = vehicle.speed,
                                rotation = -vehicle.angle,
                                flat = true,
                                onClick = {
                                    viewModel.selectVehicle(vehicle)
                                    false
                                }
                            )
                        }
                    }

                    SearchBarOverlay(
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .padding(horizontal = 16.dp, vertical = 12.dp)
                    )

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
private fun TrackingTopBar(onNotificationsClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(BluePrimary)
            .padding(horizontal = 4.dp, vertical = 10.dp)
    ) {
        IconButton(
            onClick = {},
            modifier = Modifier.align(Alignment.CenterStart)
        ) {
            Icon(
                imageVector = Icons.Filled.Menu,
                contentDescription = "Menú",
                tint = Color.White
            )
        }
        Text(
            text = "Monitorea tu Envío",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            modifier = Modifier.align(Alignment.Center)
        )
        IconButton(
            onClick = onNotificationsClick,
            modifier = Modifier.align(Alignment.CenterEnd)
        ) {
            Icon(
                imageVector = Icons.Filled.Notifications,
                contentDescription = "Notificaciones",
                tint = Color.White
            )
        }
    }
}

@Composable
private fun SearchBarOverlay(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .shadow(4.dp, RoundedCornerShape(28.dp))
            .background(Color.White, RoundedCornerShape(28.dp))
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Filled.Search,
            contentDescription = null,
            tint = TextSecondary,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "Buscar envío...",
            color = TextHint,
            fontSize = 14.sp
        )
    }
}

@Composable
private fun VehicleInfoCard(vehicle: Vehicle?, modifier: Modifier = Modifier) {
    val statusColor = when (vehicle?.status) {
        VehicleStatus.GREEN -> Color(0xFF4CAF50)
        VehicleStatus.RED -> Color(0xFFF44336)
        else -> Color(0xFF9E9E9E)
    }
    val statusLabel = when (vehicle?.status) {
        VehicleStatus.GREEN -> "En Tránsito"
        VehicleStatus.RED -> "Detenido"
        else -> "Desconocido"
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
                Text(
                    text = vehicle?.speed ?: "—",
                    color = TextSecondary,
                    fontSize = 13.sp
                )
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

private fun vehicleIcon(context: Context, resId: Int, density: Float): BitmapDescriptor {
    val src = BitmapFactory.decodeResource(context.resources, resId)
    val targetPx = (48 * density).toInt()
    val (w, h) = if (src.width >= src.height) {
        targetPx to (targetPx * src.height / src.width)
    } else {
        (targetPx * src.width / src.height) to targetPx
    }
    val scaled = Bitmap.createScaledBitmap(src, w, h, true)
    src.recycle()
    val rotated = Bitmap.createBitmap(
        scaled, 0, 0, scaled.width, scaled.height,
        Matrix().apply { postRotate(-90f) }, true
    )
    scaled.recycle()
    return BitmapDescriptorFactory.fromBitmap(rotated)
}
