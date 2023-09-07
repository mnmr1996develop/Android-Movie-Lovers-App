package com.michaelrichards.movieloversapp.screens.RegistrationScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.michaelrichards.movieloversapp.data.State
import com.michaelrichards.movieloversapp.dtos.SignUpRequest
import com.michaelrichards.movieloversapp.navigation.Graphs
import com.michaelrichards.movieloversapp.repositories.auth.AuthResult
import com.michaelrichards.movieloversapp.repositories.interfaces.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject


private const val TAG = "RegistrationViewModel"

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel () {

    var state = State(loading = false)
    private val resultChannel = Channel<AuthResult<Unit>>()
    val authResults = resultChannel.receiveAsFlow()

    fun register(
        firstName: String,
        lastName: String,
        email: String,
        username: String,
        password: String,
        birthday: LocalDate,
        navController: NavController
    ) = viewModelScope.launch {
        val response = SignUpRequest(firstName, lastName, email, username, password, birthday.toString())
        val result = repository.register(response)
        resultChannel.send(result)
        state.loading = false
    }
}