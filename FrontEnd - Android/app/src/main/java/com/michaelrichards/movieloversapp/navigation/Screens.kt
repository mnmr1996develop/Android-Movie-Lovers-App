package com.michaelrichards.movieloversapp.navigation

sealed class Screens(val route: String) {
    object SplashScreen: Screens("splash_screen")
    object RegistrationScreen: Screens("registration_screen")
    object LoginScreen: Screens("login_screen")
    object AuthGraph: Screens("auth")
    object HomeScreen: Screens("home_screen")
    object MainGraph: Screens("main")
}
