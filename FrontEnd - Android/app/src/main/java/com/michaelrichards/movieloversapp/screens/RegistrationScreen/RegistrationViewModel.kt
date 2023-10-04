package com.michaelrichards.movieloversapp.screens.RegistrationScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.michaelrichards.movieloversapp.data.DataOrException
import com.michaelrichards.movieloversapp.data.State
import com.michaelrichards.movieloversapp.dtos.SignUpRequest
import com.michaelrichards.movieloversapp.repositories.results.AuthResult
import com.michaelrichards.movieloversapp.repositories.interfaces.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject


private const val TAG = "RegistrationViewModel"

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    val state = State(loading = false)
    private val resultChannel = Channel<AuthResult<String>>()
    val authResults = resultChannel.receiveAsFlow()

    fun register(
        firstName: String,
        lastName: String,
        email: String,
        username: String,
        password: String,
        birthday: LocalDate,
    ) = viewModelScope.launch {
        state.loading = true

        val response = SignUpRequest(
            firstName,
            lastName,
            email,
            username,
            password,
            birthday.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")).toString()
        )

        val result = repository.register(response)
        resultChannel.send(result)
        state.loading = false
    }
}