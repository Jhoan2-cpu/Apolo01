package com.apolo.tracking.presentation.forgotpassword

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.apolo.tracking.presentation.components.AppPrimaryButton
import com.apolo.tracking.presentation.components.AppTextField
import com.apolo.tracking.presentation.components.AppTopBar
import com.apolo.tracking.ui.theme.CyanSecondary
import com.apolo.tracking.ui.theme.RedError
import com.apolo.tracking.ui.theme.TextSecondary

@Composable
fun ForgotPasswordScreen(
    onNavigateBack: () -> Unit,
    viewModel: ForgotPasswordViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        AppTopBar(
            title = "Recuperar Contraseña",
            onNavigateBack = onNavigateBack
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Ingresa tu usuario o correo y te enviaremos las instrucciones para recuperar tu contraseña.",
                textAlign = TextAlign.Center,
                color = TextSecondary,
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(32.dp))
            AppTextField(
                value = uiState.email,
                onValueChange = viewModel::onEmailChange,
                placeholder = "Usuario o email",
                leadingIcon = Icons.Default.Email,
                modifier = Modifier.fillMaxWidth(),
                keyboardType = KeyboardType.Email
            )
            Spacer(modifier = Modifier.height(12.dp))
            when {
                uiState.successMessage != null -> Text(
                    text = uiState.successMessage!!,
                    color = CyanSecondary,
                    fontSize = 13.sp,
                    textAlign = TextAlign.Center
                )
                uiState.errorMessage != null -> Text(
                    text = uiState.errorMessage!!,
                    color = RedError,
                    fontSize = 13.sp
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            AppPrimaryButton(
                text = "Enviar",
                onClick = viewModel::onSendClick,
                isLoading = uiState.isLoading,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextButton(onClick = onNavigateBack) {
                Text(text = "Volver al inicio de sesión", color = CyanSecondary)
            }
        }
    }
}
