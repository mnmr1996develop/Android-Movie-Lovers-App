package com.michaelrichards.movieloversapp.dtos

data class JwtAuthenticationResponse(
    val accessToken: String,
    val refreshToken: String
)
