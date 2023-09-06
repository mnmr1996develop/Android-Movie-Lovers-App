package com.michaelrichards.movieloversapp.screens.LoginScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.michaelrichards.movieloversapp.dtos.SignInRequest
import com.michaelrichards.movieloversapp.navigation.Graphs
import com.michaelrichards.movieloversapp.repositories.interfaces.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "LoginViewModel"
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: AuthRepository,
): ViewModel() {

    fun loginWithUsernameAndPassword(username: String, password: String, navController: NavController) = viewModelScope.launch {
        val signInRequest = SignInRequest(username, password)
        try {
            if (repository.login(signInRequest)){
                navController.navigate(Graphs.MainGraph.routeName){
                    popUpTo(Graphs.AuthGraph.routeName){
                        inclusive = true
                    }
                }
            }
        }catch (e: Exception){
            Log.i(TAG, "loginWithUsernameAndPassword: ${e.message}")
        }
    }
    
}