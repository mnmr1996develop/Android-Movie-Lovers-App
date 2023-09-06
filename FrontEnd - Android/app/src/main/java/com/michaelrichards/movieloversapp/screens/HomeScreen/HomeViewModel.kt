package com.michaelrichards.movieloversapp.screens.HomeScreen

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.michaelrichards.movieloversapp.navigation.Graphs
import dagger.hilt.android.lifecycle.HiltViewModel



class HomeViewModel : ViewModel(){

    fun logout(navController: NavController) {
        navController.navigate(Graphs.AuthGraph.routeName){
            popUpTo(Graphs.MainGraph.routeName){
                inclusive = true
            }
        }
    }
}