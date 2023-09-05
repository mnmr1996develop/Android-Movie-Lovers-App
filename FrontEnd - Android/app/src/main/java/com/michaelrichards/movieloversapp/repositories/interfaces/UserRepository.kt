package com.michaelrichards.movieloversapp.repositories.interfaces

interface UserRepository {
    suspend fun login(username: String, password: String): Boolean
    suspend fun register()
}