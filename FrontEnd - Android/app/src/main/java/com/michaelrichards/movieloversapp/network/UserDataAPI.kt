package com.michaelrichards.movieloversapp.network

import com.michaelrichards.movieloversapp.dtos.JwtAuthenticationResponse
import com.michaelrichards.movieloversapp.dtos.SignInRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import javax.inject.Singleton

@Singleton
interface UserDataAPI {
    @GET("auth")
    suspend fun login(@Body request: SignInRequest): JwtAuthenticationResponse
}