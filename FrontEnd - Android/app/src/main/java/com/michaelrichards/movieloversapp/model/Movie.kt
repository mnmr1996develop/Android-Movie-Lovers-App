package com.michaelrichards.movieloversapp.model

data class Movie(
    val ok: String,
    val description: List<Description>,
    val errorCode: String
)
