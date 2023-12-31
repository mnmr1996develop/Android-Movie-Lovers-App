package com.michaelrichards.movieloversapp.network

import com.michaelrichards.movieloversapp.dtos.JwtAuthenticationResponse
import com.michaelrichards.movieloversapp.dtos.SignInRequest
import com.michaelrichards.movieloversapp.dtos.SignUpRequest
import com.michaelrichards.movieloversapp.model.JwtToken
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import javax.inject.Singleton

@Singleton
interface AuthAPI {

    @POST("auth/login")
    suspend fun login(@Body request: SignInRequest): JwtAuthenticationResponse

    @POST("auth/signup")
    suspend fun register(@Body request: SignUpRequest): JwtAuthenticationResponse

    @POST("auth/logout")
    suspend fun logout(@Body jwtToken: String)

    @POST("auth/authenticate")
    suspend fun authenticate(@Body token: JwtToken)
}
