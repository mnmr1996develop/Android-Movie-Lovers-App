package com.michaelrichards.movieloversapp.repositories.interfaces

import com.michaelrichards.movieloversapp.dtos.SignInRequest
import com.michaelrichards.movieloversapp.dtos.SignUpRequest
import com.michaelrichards.movieloversapp.repositories.auth.AuthResult

interface AuthRepository {
    suspend fun login(signInRequest: SignInRequest): AuthResult<Unit>
    suspend fun register(signUpRequest: SignUpRequest) : AuthResult<Unit>

    suspend fun authenticate() : AuthResult<Unit>
}