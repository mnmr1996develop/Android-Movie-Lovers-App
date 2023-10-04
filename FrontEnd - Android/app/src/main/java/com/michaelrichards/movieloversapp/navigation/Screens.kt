package com.michaelrichards.movieloversapp.navigation

sealed class Screens(val route: String) {
    object SplashScreen: Screens("splash_screen")
    object RegistrationScreen: Screens("registration_screen")
    object LoginScreen: Screens("login_screen")
    object HomeScreen: Screens("home_screen")
    object SearchScreen: Screens("search_screen")

    object AccountScreen: Screens("account_screen")
    object FollowScreen: Screens("follow_screen")

}

