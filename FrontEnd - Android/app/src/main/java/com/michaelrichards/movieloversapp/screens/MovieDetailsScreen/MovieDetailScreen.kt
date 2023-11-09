package com.michaelrichards.movieloversapp.screens.MovieDetailsScreen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.michaelrichards.movieloversapp.components.BottomBar
import com.michaelrichards.movieloversapp.components.TopBar


@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun MovieDetailScreen(
    modifier: Modifier = Modifier,
    navController: NavController
) {

    val rating = remember {
        mutableFloatStateOf(5.0f)
    }

    val review = remember {
        mutableStateOf("")
    }

    Scaffold(
        topBar = { TopBar(navController = navController)},
        bottomBar = { BottomBar(navController = navController, itemNumber = 1)}
    ) {paddingValues ->
        Surface(
            modifier = modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            
        }    
        
    }

}