package com.michaelrichards.movieloversapp.repositories.implementaions


import com.michaelrichards.movieloversapp.dtos.SignInRequest
import com.michaelrichards.movieloversapp.dtos.SignUpRequest
import com.michaelrichards.movieloversapp.network.UserAPI
import com.michaelrichards.movieloversapp.repositories.interfaces.UserRepository

class UserRepositoryImpl (
    private val api: UserAPI
) : UserRepository {
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

}