package com.michaelrichards.movieloversapp.repositories.implementaions

import com.michaelrichards.movieloversapp.network.UserAPI
import com.michaelrichards.movieloversapp.repositories.interfaces.UserRepository

class UserRepositoryImpl (
    private val api: UserAPI
) : UserRepository {
    override suspend fun login() {
        api.login()
    }

    override suspend fun register() {
        api.register()
    }

}