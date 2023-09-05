package com.michaelrichards.movieloversapp.repositories.implementaions


import com.michaelrichards.movieloversapp.dtos.SignInRequest
import com.michaelrichards.movieloversapp.network.UserAPI
import com.michaelrichards.movieloversapp.repositories.interfaces.UserRepository

class UserRepositoryImpl (
    private val api: UserAPI
) : UserRepository {
    override suspend fun login(username: String, password: String): Boolean {
        val request = SignInRequest(username, password)
        val authToken = api.login(request) ?: return false
        return true
    }


    override suspend fun register() {
        api.register()
    }

}