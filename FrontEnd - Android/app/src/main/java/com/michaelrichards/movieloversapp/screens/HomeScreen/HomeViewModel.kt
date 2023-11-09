package com.michaelrichards.movieloversapp.screens.HomeScreen

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.michaelrichards.movieloversapp.data.DataOrException
import com.michaelrichards.movieloversapp.data.State
import com.michaelrichards.movieloversapp.dtos.UserDataDTO
import com.michaelrichards.movieloversapp.navigation.Graphs
import com.michaelrichards.movieloversapp.network.UserDataAPI
import com.michaelrichards.movieloversapp.repositories.interfaces.AuthRepository
import com.michaelrichards.movieloversapp.repositories.interfaces.UserRepository
import com.michaelrichards.movieloversapp.repositories.results.AuthResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
) : ViewModel() {
    var state = State(loading = false)
    private val resultChannel = Channel<AuthResult<Unit>>()
    val authResults = resultChannel.receiveAsFlow()

    private val TAG = "HomeViewModel"
    
    val data: MutableState<DataOrException<UserDataDTO, Boolean, Exception>> = mutableStateOf(
        DataOrException(data = null, loading = false)
    )

    init {
        getUserDetails()
    }

    fun logout() = viewModelScope.launch {
        state.loading = true
        val result = authRepository.logout()
        resultChannel.send(result)
        state.loading = false
    }

    private fun getUserDetails(): Unit {
        viewModelScope.launch {
            data.value.loading = true
            data.value = userRepository.getBasicUserDetails()
            Log.d(TAG, "getUserDetails: ${data.value.data}")
        }
    }
}