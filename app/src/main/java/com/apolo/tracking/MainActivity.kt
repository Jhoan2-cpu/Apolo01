package com.apolo.tracking

import android.os.Bundle
import androidx.activity.ComponentActivity
import dagger.hilt.android.AndroidEntryPoint
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.apolo.tracking.presentation.navigation.AppNavGraph
import com.apolo.tracking.ui.theme.ApoloTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ApoloTheme {
                AppNavGraph()
            }
        }
    }
}