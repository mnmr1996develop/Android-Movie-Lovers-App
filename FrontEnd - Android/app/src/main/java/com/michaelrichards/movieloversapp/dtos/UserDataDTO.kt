package com.michaelrichards.movieloversapp.dtos

import java.util.UUID

data class UserDataDTO(
    val firstName: String,
    val lastName: String,
    val email: String,
    val username: String,
    val birthday: String
)