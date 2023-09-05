package com.michaelrichards.movieloversapp.screens.LoginScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.michaelrichards.movieloversapp.navigation.Screens
import com.michaelrichards.movieloversapp.repositories.interfaces.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

private const val TAG = "LoginViewModel"
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: UserRepository,
): ViewModel() {

    fun loginWithUsernameAndPassword(username: String, password: String, navController: NavController) = viewModelScope.launch {
        try {
            if (repository.login(username, password)){
                navController.navigate(Screens.MainGraph.route){
                    popUpTo(Screens.AuthGraph.route){
                        inclusive = true
                    }
                }
            }
        }catch (e: Exception){
            Log.i(TAG, "loginWithUsernameAndPassword: ${e.message}")
        }
    }
    
}