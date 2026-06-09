package com.apolo.tracking.presentation.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.apolo.tracking.presentation.components.AppPrimaryButton
import com.apolo.tracking.ui.theme.BluePrimary
import com.apolo.tracking.ui.theme.CyanSecondary

@Composable
fun LoginScreen(
    onNavigateToMain: () -> Unit,
    onNavigateToForgotPassword: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Iniciar Sesión",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = BluePrimary
        )
        Spacer(modifier = Modifier.height(32.dp))
        AppPrimaryButton(
            text = "Iniciar Sesión",
            onClick = onNavigateToMain,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextButton(onClick = onNavigateToForgotPassword) {
            Text("¿Has olvidado tu contraseña?", color = CyanSecondary)
        }
    }
}
