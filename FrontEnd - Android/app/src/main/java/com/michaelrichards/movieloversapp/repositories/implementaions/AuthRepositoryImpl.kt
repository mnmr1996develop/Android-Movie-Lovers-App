package com.michaelrichards.movieloversapp.repositories.implementaions


import com.michaelrichards.movieloversapp.dtos.SignInRequest
import com.michaelrichards.movieloversapp.dtos.SignUpRequest
import com.michaelrichards.movieloversapp.network.AuthAPI
import com.michaelrichards.movieloversapp.repositories.interfaces.AuthRepository

class AuthRepositoryImpl (
    private val api: AuthAPI
) : AuthRepository {
    override suspend fun login(signInRequest: SignInRequest): Boolean {
        val authToken = api.login(signInRequest) ?: return false
        return true
    }


    override suspend fun register(
        request: SignUpRequest
    ) : Boolean {
        val authToken =  api.register(request) ?: return false
        return true
    }

    override suspend fun logout(): Boolean {
       return true
    }

}