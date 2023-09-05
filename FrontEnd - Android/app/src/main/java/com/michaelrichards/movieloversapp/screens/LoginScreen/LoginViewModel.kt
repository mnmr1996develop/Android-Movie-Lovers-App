package com.michaelrichards.movieloversapp.screens.LoginScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.michaelrichards.movieloversapp.navigation.Screens
import kotlinx.coroutines.launch
import java.lang.Exception

private const val TAG = "LoginViewModel"
private const val BASE_PATH = "http://localhost:8080/"
class LoginViewModel(): ViewModel() {

    fun loginWithUsernameAndPassword(username: String, password: String, navController: NavController) = viewModelScope.launch {
        try {
            if (username == "admin" && password == "admin"){
                navController.navigate(Screens.MainGraph.route){
                    popUpTo(Screens.AuthGraph.route){
                        inclusive = true
                    }
                }
            }
        }catch (e: Exception){
            Log.i(TAG, "loginWithUsernameAndPassword: $username and $password invalid")
        }
    }
    
}