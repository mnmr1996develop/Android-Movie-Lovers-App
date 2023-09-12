package com.michaelrichards.movieloversapp.network

import com.michaelrichards.movieloversapp.dtos.JwtAuthenticationResponse
import com.michaelrichards.movieloversapp.dtos.SignUpRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieAPI {
    @GET
    suspend fun getMovie(): JwtAuthenticationResponse?

    @GET
    suspend fun searchMovies(@Query("q") query: String) : JwtAuthenticationResponse?
}