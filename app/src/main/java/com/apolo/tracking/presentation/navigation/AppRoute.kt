package com.apolo.tracking.presentation.navigation

sealed class AppRoute(val route: String) {
    data object Splash : AppRoute("splash")
    data object Login : AppRoute("login")
    data object ForgotPassword : AppRoute("forgot_password")
    data object Main : AppRoute("main")
    data object Tracking : AppRoute("tracking")
    data object Billing : AppRoute("billing")
    data object Profile : AppRoute("profile")
    data object Notifications : AppRoute("notifications")
}
