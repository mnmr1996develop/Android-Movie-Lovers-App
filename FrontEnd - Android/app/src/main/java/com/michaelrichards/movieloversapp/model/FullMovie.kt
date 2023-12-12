package com.michaelrichards.movieloversapp.model

data class FullMovie(
    val short: Short,
    val top: Top,
    val imdbId: String
) {
    override fun toString(): String {
        return "FullMovie(short=$short, imdbId='$imdbId')"
    }
}
