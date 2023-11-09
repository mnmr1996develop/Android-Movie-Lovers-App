package com.michaelrichards.movieloversapp.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.michaelrichards.movieloversapp.screens.AccountScreen.AccountScreen
import com.michaelrichards.movieloversapp.screens.FollowingScreen.FollowingScreen
import com.michaelrichards.movieloversapp.screens.FollowingScreen.FollowingViewModel
import com.michaelrichards.movieloversapp.screens.HomeScreen.HomeScreen
import com.michaelrichards.movieloversapp.screens.HomeScreen.HomeViewModel
import com.michaelrichards.movieloversapp.screens.LoginScreen.LoginScreen
import com.michaelrichards.movieloversapp.screens.RegistrationScreen.RegistrationScreen
import com.michaelrichards.movieloversapp.screens.ReviewScreen.ReviewScreen
import com.michaelrichards.movieloversapp.screens.SearchScreen.SearchScreen
import com.michaelrichards.movieloversapp.screens.SearchScreen.SearchViewModel
import com.michaelrichards.movieloversapp.screens.SplashScreen.SplashScreen
import com.michaelrichards.movieloversapp.screens.UserDetailsScreen.UserDetailsScreen

@Composable
fun UserNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Graphs.StartGraph.routeName) {

        navigation(
            route = Graphs.StartGraph.routeName,
            startDestination = Screens.SplashScreen.route
        ) {
            composable(route = Screens.SplashScreen.route) {
                SplashScreen(navController = navController)
            }
        }

        navigation(
            route = Graphs.AuthGraph.routeName,
            startDestination = Screens.LoginScreen.route
        ) {
            composable(route = Screens.LoginScreen.route) {
                LoginScreen(navController = navController)
            }
            composable(route = Screens.RegistrationScreen.route) {
                RegistrationScreen(navController = navController)
            }
        }
        navigation(
            route = Graphs.MainGraph.routeName,
            startDestination = Screens.HomeScreen.route
        ) {
            composable(route = Screens.HomeScreen.route) {
                val viewModel = hiltViewModel<HomeViewModel>()
                HomeScreen(navController = navController, viewModel = viewModel)
            }

            val userDetailRoute = Screens.UserDetailsScreen.route
            composable(route = "$userDetailRoute/{username}", arguments = listOf(navArgument("username"){
                type = NavType.StringType
            })){backstackEntry ->
                backstackEntry.arguments?.getString("username").let {
                    UserDetailsScreen(navController = navController, username = it.toString())
                }
            }

            composable(route = Screens.SearchScreen.route) {
                val viewModel = hiltViewModel<SearchViewModel>()
                SearchScreen(navController = navController, viewModel = viewModel)
            }

            composable(route = Screens.AccountScreen.route) {
                AccountScreen(navController = navController)
            }

            composable(route = Screens.FollowScreen.route) {
                val viewModel = hiltViewModel<FollowingViewModel>()
                FollowingScreen(navController = navController, viewModel = viewModel)
            }


            composable(route = "${Screens.ReviewScreen.route}/{imbdId}", arguments = listOf(
                navArgument("imbdId"){
                    type = NavType.StringType
                }
            )){backStackEntry ->
                backStackEntry.arguments?.getString("imbdId").let {
                    ReviewScreen(navController = navController, imbdId = it.toString())
                }

            }

        }
    }
}

