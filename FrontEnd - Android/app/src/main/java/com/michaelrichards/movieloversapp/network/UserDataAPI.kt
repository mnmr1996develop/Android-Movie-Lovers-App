package com.michaelrichards.movieloversapp.network

import com.michaelrichards.movieloversapp.dtos.JwtAuthenticationResponse
import com.michaelrichards.movieloversapp.dtos.SignInRequest
import com.michaelrichards.movieloversapp.dtos.UserDataDTO
import com.michaelrichards.movieloversapp.model.JwtToken
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import javax.inject.Singleton

@Singleton
interface UserDataAPI {
    @GET("users")
    suspend fun getUserBasicDetails(@Header("Authorization") token: String): UserDataDTO
}