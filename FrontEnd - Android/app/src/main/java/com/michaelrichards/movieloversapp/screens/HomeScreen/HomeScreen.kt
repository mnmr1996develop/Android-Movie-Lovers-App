package com.michaelrichards.movieloversapp.screens.HomeScreen

import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun HomeScreen(navController: NavController) {
     Surface {
          Text(text = "greetings")
     }
}