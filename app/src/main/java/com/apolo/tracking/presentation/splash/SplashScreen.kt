package com.apolo.tracking.presentation.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.apolo.tracking.ui.theme.BluePrimary
import com.apolo.tracking.ui.theme.CyanSecondary
import com.apolo.tracking.ui.theme.PurpleTertiary
import com.apolo.tracking.ui.theme.TextSecondary
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onNavigateToLogin: () -> Unit) {
    LaunchedEffect(Unit) {
        delay(1500L)
        onNavigateToLogin()
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            ApoloLogo(size = 140)
            Spacer(modifier = Modifier.height(28.dp))
            Text(
                text = "Apolo",
                fontSize = 34.sp,
                fontWeight = FontWeight.ExtraBold,
                color = BluePrimary
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Monitoreo de Envíos",
                fontSize = 14.sp,
                color = TextSecondary
            )
        }
    }
}

@Composable
internal fun ApoloLogo(size: Int = 120) {
    Box(
        modifier = Modifier
            .size(size.dp)
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(PurpleTertiary, CyanSecondary, BluePrimary)
                ),
                shape = RoundedCornerShape((size / 4).dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "A",
            fontSize = (size * 0.46f).sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color.White
        )
    }
}
