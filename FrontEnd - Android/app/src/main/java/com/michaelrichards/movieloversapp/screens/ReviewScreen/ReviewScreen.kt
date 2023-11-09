package com.michaelrichards.movieloversapp.screens.ReviewScreen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun ReviewScreen (
    navController: NavController,
    viewModel: ReviewViewModel = ReviewViewModel(),
    imbdId: String
){

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {

    }
}