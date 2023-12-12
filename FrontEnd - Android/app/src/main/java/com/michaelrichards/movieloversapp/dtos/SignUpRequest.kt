package com.michaelrichards.movieloversapp.dtos

data class SignUpRequest(
    val firstName: String,
    val lastName: String,
    val email: String,
    val username: String,
    val password: String,
    val birthday: String
)