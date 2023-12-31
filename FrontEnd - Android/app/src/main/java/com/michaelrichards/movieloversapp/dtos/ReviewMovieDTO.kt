package com.michaelrichards.movieloversapp.dtos

data class ReviewMovieDTO(
    val imdbId: String,
    val rating: Int,
    val title: String,
    val imagePosterLink: String,
    val actors: String,
    val imdbIdUrl: String,
    val description: String,
)
