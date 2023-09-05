package com.michaelrichards.movieloversapp.screens.RegistrationScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.michaelrichards.movieloversapp.dtos.SignUpRequest
import com.michaelrichards.movieloversapp.navigation.Screens
import com.michaelrichards.movieloversapp.repositories.interfaces.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject


private const val TAG = "RegistrationViewModel"

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel () {

    fun register(
        firstName: String,
        lastName: String,
        email: String,
        username: String,
        password: String,
        birthday: LocalDate,
        navController: NavController
    ) = viewModelScope.launch {
        val request = SignUpRequest(firstName, lastName, email, username, password, birthday.toString())
        println(request)
        try {
            if (repository.register(signUpRequest = request)){
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