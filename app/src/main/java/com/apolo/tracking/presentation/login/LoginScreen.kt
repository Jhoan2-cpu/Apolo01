package com.apolo.tracking.presentation.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import com.apolo.tracking.presentation.components.AppPasswordField
import com.apolo.tracking.presentation.components.AppPrimaryButton
import com.apolo.tracking.presentation.components.AppTextField
import com.apolo.tracking.presentation.splash.ApoloLogo
import com.apolo.tracking.ui.theme.CyanSecondary
import com.apolo.tracking.ui.theme.RedError

@Composable
fun LoginScreen(
    onNavigateToMain: () -> Unit,
    onNavigateToForgotPassword: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState.isLoggedIn) {
        if (uiState.isLoggedIn) onNavigateToMain()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(64.dp))
        ApoloLogo(size = 110)
        Spacer(modifier = Modifier.height(48.dp))
        AppTextField(
            value = uiState.email,
            onValueChange = viewModel::onEmailChange,
            placeholder = "Usuario",
            leadingIcon = Icons.Default.Person,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        AppPasswordField(
            value = uiState.password,
            onValueChange = viewModel::onPasswordChange,
            placeholder = "Contraseña",
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Checkbox(
                checked = uiState.rememberSession,
                onCheckedChange = viewModel::onRememberSessionChange,
                colors = CheckboxDefaults.colors(
                    checkedColor = CyanSecondary,
                    uncheckedColor = CyanSecondary
                )
            )
            Text(
                text = "Mantener sesión activa",
                color = CyanSecondary,
                fontWeight = FontWeight.Medium
            )
        }
        if (uiState.errorMessage != null) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = uiState.errorMessage!!,
                color = RedError,
                fontSize = 13.sp,
                modifier = Modifier.fillMaxWidth()
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        AppPrimaryButton(
            text = "Iniciar Sesión",
            onClick = viewModel::onLoginClick,
            isLoading = uiState.isLoading,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(20.dp))
        TextButton(onClick = onNavigateToForgotPassword) {
            Text(
                text = "¿Has olvidado tu contraseña?",
                color = CyanSecondary,
                fontWeight = FontWeight.Medium
            )
        }
        Spacer(modifier = Modifier.height(32.dp))
    }
}
