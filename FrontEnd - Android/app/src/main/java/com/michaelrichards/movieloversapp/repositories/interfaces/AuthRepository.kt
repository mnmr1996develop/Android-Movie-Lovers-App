package com.michaelrichards.movieloversapp.repositories.interfaces

import com.michaelrichards.movieloversapp.dtos.SignInRequest
import com.michaelrichards.movieloversapp.dtos.SignUpRequest

interface AuthRepository {
    suspend fun login(signInRequest: SignInRequest): Boolean
    suspend fun register(signUpRequest: SignUpRequest) : Boolean

    suspend fun logout() : Boolean
}