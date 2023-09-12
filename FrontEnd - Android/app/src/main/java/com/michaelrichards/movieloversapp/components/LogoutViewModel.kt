package com.michaelrichards.movieloversapp.components

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.michaelrichards.movieloversapp.data.State
import com.michaelrichards.movieloversapp.repositories.auth.AuthResult
import com.michaelrichards.movieloversapp.repositories.interfaces.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LogoutViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    var state = State(loading = false)
    private val resultChannel = Channel<AuthResult<Unit>>()
    val authResults = resultChannel.receiveAsFlow()

    fun logout() = viewModelScope.launch {
        state.loading = true
        val result = repository.logout()
        resultChannel.send(result)
        state.loading = false
    }
}