package com.michaelrichards.movieloversapp.model

data class Movie(
    val imdbID: String,
    val name: String,
    val description: String,
    val url: String,
    val genre: List<String>,
    val datePublished: String,
    val actor: List<Actor>,
    val director: List<Director>
)