package com.example.ui.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object SignUp : Screen("sign_up")
    object Login : Screen("login")
    object Register : Screen("register")
    object Home : Screen("home")
    object Profile : Screen("profile")
    object OrderAddress : Screen("order_address")
    object OrderConfirmation : Screen("order_confirmation")
}

enum class BottomTab {
    HOME,
    ORDER,
    PROFILE
}
