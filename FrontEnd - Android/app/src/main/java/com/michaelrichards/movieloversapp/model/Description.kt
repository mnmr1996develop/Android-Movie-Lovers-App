package com.michaelrichards.movieloversapp.model

import com.google.gson.annotations.SerializedName


data class Description(
    @SerializedName("#TITLE")
    val title: String,

    @SerializedName("#YEAR")
    val year: Int,

    @SerializedName("#IMDB_ID")
    val imdbId: String,

    @SerializedName("#RANK")
    val rank: Int,

    @SerializedName("#ACTORS")
    val actors: String,

    @SerializedName("#AKA")
    val aka: String,

    @SerializedName("#IMDB_URL")
    val imdbUrl: String,

    @SerializedName("#IMG_POSTER")
    val imagePosterLink: String,

    @SerializedName("photo_width")
    val photoWidth: Int,

    @SerializedName("photo_height")
    val photoHeight: Int
)