package com.michaelrichards.movieloversapp.model

import com.google.gson.annotations.SerializedName

data class Crew(
    @SerializedName("@type")
    val type: String,
    val name: String?,
    val url: String,
)
