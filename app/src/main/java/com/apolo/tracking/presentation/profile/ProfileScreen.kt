package com.apolo.tracking.presentation.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.apolo.tracking.presentation.components.AppPrimaryButton
import com.apolo.tracking.ui.theme.BluePrimary

@Composable
fun ProfileScreen(onLogout: () -> Unit = {}) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Perfil de Usuario", color = BluePrimary)
        Spacer(modifier = Modifier.height(24.dp))
        AppPrimaryButton(
            text = "Cerrar sesión",
            onClick = onLogout
        )
    }
}
