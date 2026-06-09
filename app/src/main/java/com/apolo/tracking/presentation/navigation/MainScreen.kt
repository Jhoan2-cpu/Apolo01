package com.apolo.tracking.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.apolo.tracking.presentation.billing.BillingScreen
import com.apolo.tracking.presentation.components.AppBottomNavigationBar
import com.apolo.tracking.presentation.notifications.NotificationsScreen
import com.apolo.tracking.presentation.profile.ProfileScreen
import com.apolo.tracking.presentation.tracking.TrackingScreen

@Composable
fun MainScreen(onLogout: () -> Unit = {}) {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route ?: AppRoute.Tracking.route

    Scaffold(
        bottomBar = {
            AppBottomNavigationBar(
                currentRoute = currentRoute,
                onNavigate = { route ->
                    navController.navigate(route) {
                        popUpTo(AppRoute.Tracking.route) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = AppRoute.Tracking.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(AppRoute.Tracking.route) {
                TrackingScreen(onLogout = onLogout)
            }
            composable(AppRoute.Billing.route) { BillingScreen() }
            composable(AppRoute.Profile.route) { ProfileScreen(onLogout = onLogout) }
            composable(AppRoute.Notifications.route) { NotificationsScreen() }
        }
    }
}
