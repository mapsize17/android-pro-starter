package com.starter.app.navigation

sealed class Route(val route: String) {
    data object Login : Route("login")
    data object SignUp : Route("signup")
    data object Home : Route("home")
    data object Settings : Route("settings")
}
