package com.michaelrichards.movieloversapp.navigation

import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.michaelrichards.movieloversapp.screens.HomeScreen.HomeScreen
import com.michaelrichards.movieloversapp.screens.LoginScreen.LoginScreen
import com.michaelrichards.movieloversapp.screens.RegistrationScreen.RegistrationScreen
import com.michaelrichards.movieloversapp.screens.SearchScreen.SearchScreen

@Composable
fun UserNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Graphs.MainGraph.routeName){

        navigation(route= Graphs.StartGraph.routeName, startDestination = Screens.SplashScreen.route){
            composable(route = Screens.SplashScreen.route){
                @Composable
                fun SplashScreen() {
                    Surface {
                        Text(text = "Splash Screen")
                    }
                }
            }
        }

        navigation(route = Graphs.AuthGraph.routeName, startDestination = Screens.LoginScreen.route){
            composable(route = Screens.LoginScreen.route){
                    LoginScreen(navController = navController)
            }
            composable(route = Screens.RegistrationScreen.route){
                    RegistrationScreen(navController = navController)
            }
        }
        navigation(route = Graphs.MainGraph.routeName, startDestination = Screens.HomeScreen.route){
                composable(route = Screens.HomeScreen.route){
                    HomeScreen(navController = navController)
                }
                
                composable(route = Screens.SearchScreen.route){
                    SearchScreen(navController = navController)
                }

        }
    }
}

