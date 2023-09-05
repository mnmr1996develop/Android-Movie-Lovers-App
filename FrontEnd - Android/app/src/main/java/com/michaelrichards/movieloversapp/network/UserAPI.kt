package com.michaelrichards.movieloversapp.network

import com.michaelrichards.movieloversapp.dtos.JwtAuthenticationResponse
import com.michaelrichards.movieloversapp.dtos.SignInRequest
import retrofit2.http.Body
import retrofit2.http.POST
import javax.inject.Singleton

@Singleton
interface UserAPI {

    @POST("auth/login")
    suspend fun login(@Body request: SignInRequest): JwtAuthenticationResponse?

    @POST("auth/register")
    suspend fun register() : JwtAuthenticationResponse
}
