package com.michaelrichards.movieloversapp.network

import com.michaelrichards.movieloversapp.model.JwtAuthenticationResponse
import retrofit2.http.POST
import javax.inject.Singleton

@Singleton
interface UserAPI {

    @POST("auth/login")
    suspend fun login(): JwtAuthenticationResponse

    @POST("auth/register")
    suspend fun register() : JwtAuthenticationResponse
}
