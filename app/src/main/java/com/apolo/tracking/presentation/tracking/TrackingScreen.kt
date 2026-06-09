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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.apolo.tracking.presentation.components.AppTopBar
import com.apolo.tracking.ui.theme.BluePrimary
import com.apolo.tracking.ui.theme.CyanSecondary
import com.apolo.tracking.ui.theme.TextSecondary

@Composable
fun TrackingScreen() {
    Column(modifier = Modifier.fillMaxSize()) {
        AppTopBar(
            title = "Monitorea tu Envío",
            onActionClick = {}
        )
        Box(modifier = Modifier.fillMaxSize()) {
            // Placeholder — replaced with GoogleMap in a later step
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFDDE8DD)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Mapa de vehículos",
                    color = Color(0xFF7A9E7A),
                    fontSize = 16.sp
                )
            }
            VehicleInfoCard(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            )
        }
    }
}

@Composable
private fun VehicleInfoCard(modifier: Modifier = Modifier) {
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
                    .background(Color(0xFF4CAF50), CircleShape)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "DMO-123",
                    fontWeight = FontWeight.Bold,
                    color = BluePrimary,
                    fontSize = 16.sp
                )
                Text(
                    text = "45 km/h",
                    color = TextSecondary,
                    fontSize = 13.sp
                )
            }
            Text(
                text = "En Tránsito",
                color = CyanSecondary,
                fontWeight = FontWeight.SemiBold,
                fontSize = 13.sp
            )
        }
    }
}
