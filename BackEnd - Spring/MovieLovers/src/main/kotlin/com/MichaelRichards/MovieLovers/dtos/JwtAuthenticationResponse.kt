package com.MichaelRichards.MovieLovers.dtos

data class JwtAuthenticationResponse(
    val accessToken: String,
    val refreshToken: String
)
