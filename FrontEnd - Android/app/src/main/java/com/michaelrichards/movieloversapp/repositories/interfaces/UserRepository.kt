package com.michaelrichards.movieloversapp.repositories.interfaces

interface UserRepository {
    suspend fun login()
    suspend fun register()
}