package com.michaelrichards.movieloversapp.network

import com.michaelrichards.movieloversapp.dtos.JwtAuthenticationResponse
import com.michaelrichards.movieloversapp.dtos.SignUpRequest
import retrofit2.http.Body
import retrofit2.http.GET

interface MovieAPI {
    @GET("auth/login")
    suspend fun getMovie(): JwtAuthenticationResponse?

    @GET("auth/signup")
    suspend fun searchMovie(@Body request: SignUpRequest) : JwtAuthenticationResponse?
}