package com.michaelrichards.movieloversapp.model

data class Movie(
    val title: String,
    val year: Int,
    val imdbId: String,
    val rank: Int,
    val topActors: List<Actor>,
    val aka: String,
    val imdbUrl: String,
    val imagePosterLink: String,
    val photoWidth: Int,
    val photoHeight: Int
)