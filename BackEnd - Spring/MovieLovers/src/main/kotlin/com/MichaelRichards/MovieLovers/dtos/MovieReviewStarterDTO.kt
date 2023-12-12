package com.MichaelRichards.MovieLovers.dtos

data class MovieReviewStarterDTO(
    val imdbId: String,
    val rating: Int,
    val title: String,
    val imagePosterLink: String,
    val actors: String,
    val imdbIdUrl: String,
    val description: String,
)


