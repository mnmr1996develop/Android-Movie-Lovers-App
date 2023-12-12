package com.michaelrichards.movieloversapp.screens.AccountScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.michaelrichards.movieloversapp.data.State
import com.michaelrichards.movieloversapp.repositories.interfaces.AuthRepository
import com.michaelrichards.movieloversapp.repositories.results.AuthResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountScreenViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    var state = State(loading = false)
    private val resultChannel = Channel<AuthResult<Unit>>()
    val authResults = resultChannel.receiveAsFlow()

    fun logout() = viewModelScope.launch {
        state.loading = true
        val result = authRepository.logout()
        resultChannel.send(result)
        state.loading = false
    }
}