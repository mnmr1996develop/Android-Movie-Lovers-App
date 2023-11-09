package com.MichaelRichards.MovieLovers.dtos

data class MovieReviewFullDataDTO(
    val reviewId: Long,
    val imdbId: String,
    val rating: Int,
    val title: String,
    val imagePosterLink: String,
    val actors: String,
    val imdbIdUrl: String,
    val description: String,
)
