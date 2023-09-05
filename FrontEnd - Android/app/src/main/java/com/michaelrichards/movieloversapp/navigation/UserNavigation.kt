package com.michaelrichards.movieloversapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.michaelrichards.movieloversapp.screens.HomeScreen.HomeScreen
import com.michaelrichards.movieloversapp.screens.LoginScreen.LoginScreen
import com.michaelrichards.movieloversapp.screens.RegistrationScreen.RegistrationScreen

@Composable
fun UserNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screens.AuthGraph.route){

        navigation(route = Screens.AuthGraph.route, startDestination = Screens.LoginScreen.route){
            composable(route = Screens.LoginScreen.route){
                    LoginScreen(navController = navController)
            }
            composable(route = Screens.RegistrationScreen.route){
                    RegistrationScreen(navController = navController)
            }
        }
        navigation(route = Screens.MainGraph.route, startDestination = Screens.HomeScreen.route){
                composable(route = Screens.HomeScreen.route){
                    HomeScreen(navController = navController)
                }

        }
    }
}

