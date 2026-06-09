package com.apolo.tracking.presentation.profile

import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.apolo.tracking.presentation.components.AppTopBar
import com.apolo.tracking.ui.theme.BluePrimary
import com.apolo.tracking.ui.theme.CyanSecondary
import com.apolo.tracking.ui.theme.RedError
import com.apolo.tracking.ui.theme.SurfaceLight
import com.apolo.tracking.ui.theme.TextSecondary

@Composable
fun ProfileScreen(onLogout: () -> Unit = {}) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SurfaceLight)
    ) {
        AppTopBar(title = "Perfil de Usuario")
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            AvatarSection()
            Spacer(modifier = Modifier.height(32.dp))
            InfoCard()
            Spacer(modifier = Modifier.height(24.dp))
            LogoutButton(onLogout = onLogout)
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun AvatarSection() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(96.dp)
                .background(
                    brush = Brush.linearGradient(listOf(BluePrimary, CyanSecondary)),
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "A",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 40.sp
            )
        }
        Spacer(modifier = Modifier.height(14.dp))
        Text(
            text = "admin",
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp,
            color = BluePrimary
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Administrador",
            fontSize = 14.sp,
            color = TextSecondary
        )
    }
}

@Composable
private fun InfoCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(4.dp)) {
            InfoRow(
                icon = Icons.Default.Person,
                label = "Usuario",
                value = "admin"
            )
            RowDivider()
            InfoRow(
                icon = Icons.Default.Email,
                label = "Correo",
                value = "admin@apolo.com"
            )
            RowDivider()
            InfoRow(
                icon = Icons.Default.CalendarMonth,
                label = "Miembro desde",
                value = "Enero 2026"
            )
            RowDivider()
            InfoRow(
                icon = Icons.Default.LocalShipping,
                label = "Envíos activos",
                value = "2"
            )
        }
    }
}

@Composable
private fun InfoRow(icon: ImageVector, label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .background(BluePrimary.copy(alpha = 0.08f), RoundedCornerShape(10.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = BluePrimary,
                modifier = Modifier.size(18.dp)
            )
        }
        Spacer(modifier = Modifier.width(14.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = label, color = TextSecondary, fontSize = 12.sp)
            Text(text = value, fontWeight = FontWeight.SemiBold, color = BluePrimary, fontSize = 14.sp)
        }
    }
}

@Composable
private fun RowDivider() {
    HorizontalDivider(
        modifier = Modifier.padding(horizontal = 16.dp),
        thickness = 0.5.dp,
        color = Color(0xFFE0E0E0)
    )
}

@Composable
private fun LogoutButton(onLogout: () -> Unit) {
    Button(
        onClick = onLogout,
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp),
        shape = RoundedCornerShape(14.dp),
        colors = ButtonDefaults.buttonColors(containerColor = RedError)
    ) {
        Icon(
            imageVector = Icons.Default.Logout,
            contentDescription = null,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "Cerrar sesión",
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp
        )
    }
}
